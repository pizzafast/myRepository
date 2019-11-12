package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public EasyUIDataGrid seBylPage(int page, int rows) {
		//设置分页条件   分页插件的实际作用就是在原来的查询全部的语句后面加上  limit page,rows  语句就实现了分页查询
		PageHelper.startPage(page, rows);
		//设置sql语句即查询全部的语句
		//如果表中有一个或一个以上的列是text类型. 查询方法要为xxxxxxxxWithBlobs()才能把表中类型为text的数据赋值到实体类中
		//如果使用xxxxWithBlobs() 查询结果中包含带有text类型 的列,如果没有使用 withblobs() 方法不带有 text 类型. 
		List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//这个作用就是在原来的查询全部的语句后面加上  limit page,rows  语句就实现了分页查询
		PageInfo<TbItemParam> pi = new PageInfo<>(params);
		EasyUIDataGrid uid = new EasyUIDataGrid();
		uid.setRows(pi.getList());
		uid.setTotal(pi.getTotal());
		return uid;
	}
	
	@Override
	public TbItemParam selByCatID(long itemCatId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(itemCatId);
		List<TbItemParam> params = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(params.size()>0){
			return params.get(0);
		}
		return null;
	}
	
	@Override
	public int delById(String ids) throws Exception {
		//将id分割出来
		String[] idStr = ids.split(",");
		int index=0;
		//如果一条删除错误就进行事务回滚   事务的原子性
		try {
			for (String id : idStr) {
				index+= tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//删除成功
		if(index==idStr.length){
			return 1;
		}else{//删除失败  进行回滚
			throw new Exception("删除失败.可能原因:数据已经不存在");
		}
	}
	
	@Override
	public int insParam(TbItemParam tbItemParam) {
		return tbItemParamMapper.insertSelective(tbItemParam);
	}

}
