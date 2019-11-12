package com.ego.search.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService {
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient;

	@Override
	public void init() throws Exception {
		//先删除所有的对象
		solrClient.deleteByQuery("*:*");
		solrClient.commit();
		//查询所有正常的商品
		List<TbItem> tbItems = tbItemDubboServiceImpl.selByStatus((byte) 1);
		for (TbItem item : tbItems) {
			// 获取类目对象
			TbItemCat cat = tbItemCatDubboServiceImpl.selById(item.getCid());
			// 获取商品描述对象
			TbItemDesc desc = tbItemDescDubboServiceImpl.selByItemId(item.getId());

			// 获取solr document对象 为了将数据插入到solr中
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", item.getId());
			doc.setField("item_title", item.getTitle());
			doc.setField("item_sell_point", item.getSellPoint());
			doc.setField("item_price", item.getPrice());
			doc.setField("item_image", item.getImage());
			doc.setField("item_category_name", cat.getName());
			doc.setField("item_desc", desc.getItemDesc());
			doc.setField("item_updated", item.getUpdated());

			// 将数据插入到solr中
			solrClient.add(doc);
		}
		// 增删改需要提交事务 和数据库一样
		solrClient.commit();
	}

	@Override
	public Map<String, Object> selByQuery(String query, int rows, int page) throws Exception {
		// 新建solr查询对象
		SolrQuery squery = new SolrQuery();
		// 设置solr分页查询条件
		squery.setStart(rows * (page - 1));// solr中查询是从第几条开始
		// 设置一页的数据量
		squery.setRows(rows);
		//排序
		squery.setSort("item_updated", ORDER.desc);
		// 设置查询条件
		squery.setQuery("item_keywords:" + query);// "item_keywords:\""+query+"\""表示query不分词查询
		// 设置高亮
		squery.setHighlight(true);// true为开启高亮
		squery.addHighlightField("item_title");// 设置属性高亮 开启高亮后 高亮属性必须添加
		// 设置高亮属性的样式 在高亮值前面添加
		squery.setHighlightSimplePre("<span style='color:red'>");
		// 在高亮值后面添加
		squery.setHighlightSimplePost("</span>");

		// 获取查询后的返回的数据 即为solr中的responseHeader
		QueryResponse response = solrClient.query(squery);

		// 获取查询结果的对象 即solr中的response 可以获取数据量
		SolrDocumentList results = response.getResults();
		// 获取高亮属性对象 即solr中的highlighting
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		List<TbItemChild> childs = new ArrayList<TbItemChild>();

		for (SolrDocument doc : results) {
			TbItemChild child = new TbItemChild();
			// 将solr中获取的数据解析到对象中
			child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			List<String> list = highlighting.get(doc.getFieldValue("id")).get("item_title");
			if (list != null && list.size() > 0) {
				child.setTitle(list.get(0));
			} else {
				child.setTitle(doc.getFieldValue("item_title").toString());
			}
			child.setPrice((Long) doc.getFieldValue("item_price"));
			Object image = doc.getFieldValue("item_image");
			// 因为前端中图像一定有对象 所以必须设置数组
			child.setImages(image == null || image.equals("") ? new String[1] : image.toString().split(","));
			child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
			childs.add(child);
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("itemList", childs);
		resultMap.put("totalPages",
				results.getNumFound() % rows == 0 ? results.getNumFound() / rows : results.getNumFound() / rows + 1);
		return resultMap;

	}

	@Override
	public int addToSolr(Map<String, Object> map, String desc) throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", map.get("id"));
		doc.setField("item_title", map.get("title"));
		doc.setField("item_sell_point", map.get("sellPoint"));
		doc.setField("item_price", map.get("price"));
		doc.setField("item_image", map.get("image"));
		doc.setField("item_category_name", tbItemCatDubboServiceImpl.selById((Integer) map.get("cid")).getName());
		System.out.println(map.get("updated"));
		doc.setField("item_updated",new Date((long) map.get("updated")));
		doc.setField("item_desc", desc);
		// 获取插入solr的结果 如果为0表示成功 其他表示失败
		UpdateResponse response = solrClient.add(doc);
		solrClient.commit();
		if (response.getStatus() == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteFromSolr(long id) throws Exception {
		UpdateResponse result = solrClient.deleteById(""+id);
		solrClient.commit();
		if (result.getStatus() == 0) {
			return 1;
		}
		return 0;
	}

}
