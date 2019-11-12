package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public EasyUIDataGrid show(int page, int rows) {
		//进行分页  page为第几页  rows为一页的数据量
		PageHelper.startPage(page, rows);
		List<TbItem> tbItems = tbItemMapper.selectByExample(new TbItemExample());
		//真分页查询
		PageInfo<TbItem> pi = new PageInfo<>(tbItems);
		EasyUIDataGrid eu = new EasyUIDataGrid();
		eu.setRows(pi.getList());
		eu.setTotal(pi.getTotal());
		return eu;
	}

	@Override
	public int updItemStatus(TbItem tbItem) {
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}

	@Override
	public int insItem(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception {
		int index=0;
		//因为存在声明式事务  所以需要trycatch  让代码继续运行   出现错误时可以事务回滚
		//因为前端中点击提交会新增三个表的数据  需要事务回滚  如果将这三个插入执行不在一个方法中 无法实现事务回滚
		try {
			index+= tbItemMapper.insertSelective(tbItem);
			index+=tbItemDescMapper.insertSelective(tbItemDesc);
			index+=tbItemParamItemMapper.insertSelective(tbItemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==3){
			return 1;
		}else{
			//为了让出现错误时前台能够提示用户
			throw new Exception("商品新增失败，原因可能是数据不完全");
		}
	}

	@Override
	public int updateItem(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception {
		int index=0;
		try {
			index+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
			index+=tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
			index+=tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//一条出错就事务回滚
		if(index==3){
			return 1;
		}else{
			//为了让出现错误时前台能够提示用户
			throw new Exception("商品修改失败，原因可能是数据不存在");
		}
	}

	@Override
	public List<TbItem> selByStatus(byte status) {
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		return tbItemMapper.selectByExample(example);
	}

	@Override
	public TbItem selById(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
