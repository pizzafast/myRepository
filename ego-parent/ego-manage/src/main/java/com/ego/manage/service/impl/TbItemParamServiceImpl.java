package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
@Service
public class TbItemParamServiceImpl implements TbItemParamService {
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		EasyUIDataGrid grids = tbItemParamDubboServiceImpl.seBylPage(page, rows);
		List<TbItemParam> params = (List<TbItemParam>) grids.getRows();
		List<TbItemParamChild> childs=new ArrayList<TbItemParamChild>();
		for (TbItemParam param : params) {
			//下转型的使用必须要在上转型的前提下使用
			TbItemParamChild child = new TbItemParamChild();
			
			child.setId(param.getId());
			child.setItemCatId(param.getItemCatId());
			child.setParamData(param.getParamData());
			child.setCreated(param.getCreated());
			child.setUpdated(param.getUpdated());
			child.setItemCatName(tbItemCatDubboServiceImpl.selById(param.getItemCatId()).getName());
			
			childs.add(child);
			
		}
		grids.setRows(childs);
		return grids;
	}
	@Override
	public int delParam(String ids) throws Exception {
		return tbItemParamDubboServiceImpl.delById(ids);
	}
	@Override
	public EgoResult selByCatId(long cid) {
		EgoResult ego = new EgoResult();
		TbItemParam param = tbItemParamDubboServiceImpl.selByCatID(cid);
		//这里返回EgoResult  控制器就可以少写代码
		if(param!=null){
			ego.setStatus(200);
			ego.setData(param);
		}
		return ego;
	}
	@Override
	public EgoResult insParam(TbItemParam tbItemParam) {
		EgoResult ego = new EgoResult();
		Date date = new Date();
		tbItemParam.setCreated(date);
		tbItemParam.setUpdated(date);
		int index = tbItemParamDubboServiceImpl.insParam(tbItemParam);
		if(index>0){
			ego.setStatus(200);
		}
		return ego;
	}

}
