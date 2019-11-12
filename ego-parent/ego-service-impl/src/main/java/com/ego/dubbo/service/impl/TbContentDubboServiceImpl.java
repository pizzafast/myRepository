package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService {
	@Resource
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGrid selByPage(long categoryId, int page, int rows) {
		// 设置分页条件
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		// 因为前台默认 categoryId为0 所以当为0时查询全部
		if (categoryId != 0) {
			//降序排序
			example.setOrderByClause("updated desc");
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);
		// 查询结果都在pi中
		PageInfo<TbContent> pi = new PageInfo<>(tbContents);
		EasyUIDataGrid ui = new EasyUIDataGrid();
		ui.setRows(pi.getList());
		ui.setTotal(pi.getTotal());
		return ui;
	}

	@Override
	public int insCate(TbContent tbContent) {
		return tbContentMapper.insertSelective(tbContent);
	}

	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		TbContentExample example = new TbContentExample();
		//如果需要排序就设置排序条件
		if(isSort){
			example.setOrderByClause("updated desc");
		}
		//count=0就查询全部
		if(count!=0){
			//设置分页条件
			PageHelper.startPage(1,count);
			List<TbContent> contents = tbContentMapper.selectByExampleWithBLOBs(example);
			//从数据库取出的数据都存在pageinfo
			PageInfo<TbContent> pi = new PageInfo<>(contents);
			return pi.getList();
		}else{
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}
}
