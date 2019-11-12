package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{
	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
	@Override
	public List<EasyUiTree> selAllCategroy(long pid) {
		List<EasyUiTree> trees=new ArrayList<EasyUiTree>();
		//查出所有的父类内容分类
		List<TbContentCategory> parent = tbContentCategoryDubboServiceImpl.selByPid(pid);
		for (TbContentCategory category : parent) {
			EasyUiTree tree=new EasyUiTree();
			//设置类目的id
			tree.setId(category.getId());
			//设置类目的名称
			tree.setText(category.getName());
			//设置类目是否为父类目   如果是父类目 默认关闭  点击时可以打开  open表示默认打开
			tree.setState(category.getIsParent()?"closed":"open");
			trees.add(tree);
		}
		return trees;
	}
	@Override
	public EgoResult insCategory(TbContentCategory category) throws Exception {
		EgoResult ego=new EgoResult();
		List<TbContentCategory> cates = tbContentCategoryDubboServiceImpl.selByPid(category.getParentId());
		//名称不能与同一父类目下的类目名称相同
		for (TbContentCategory cate : cates) {
			if(cate.getName().equals(category.getName())){
				ego.setData("名称不能与此类目下已有的相同！");
				return ego;//表示新增失败  提示用户名称不能相同
			}
		}
		
		long id = IDUtils.genItemId();//自动设置值   前端需要传入id
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		category.setIsParent(false);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setId(id);
		
		TbContentCategory categoryParent = new TbContentCategory();
		categoryParent.setId(category.getParentId());
		categoryParent.setIsParent(true);
		categoryParent.setUpdated(category.getCreated());
		
		int index = tbContentCategoryDubboServiceImpl.insCategory(category,categoryParent);
		if(index>0){
			ego.setStatus(200);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", id);
			ego.setData(map);
		}
		return ego;
	}
	@Override
	public EgoResult updCategry(TbContentCategory category) {
		EgoResult ego = new EgoResult();
		TbContentCategory categ = tbContentCategoryDubboServiceImpl.selById(category.getId());
		List<TbContentCategory> cates = tbContentCategoryDubboServiceImpl.selByPid(categ.getParentId());
		for (TbContentCategory cate : cates) {
			if(cate.getName().equals(category.getName())){
				ego.setData("名称不能与此类目下已有的相同！");
				return ego;
			}
			
		}
		Date date = new Date();
		//更新的同时更新时间
		category.setUpdated(date);
		int index = tbContentCategoryDubboServiceImpl.updCategory(category);
		if(index>0){
			ego.setStatus(200);
		}
		return ego;
	}
	@Override
	public int del(TbContentCategory category) throws Exception {
		Date date = new Date();
		category.setUpdated(date);
		category.setStatus(2);//逻辑删除
		//未实现事务回滚
//		int index=0;
//		index += tbContentCategoryDubboServiceImpl.updCategory(category);
//		//如果删除后父类没有子类目了将其设置为不是父类
//		if(index>0){
//			TbContentCategory cate = tbContentCategoryDubboServiceImpl.selById(category.getId());
//			List<TbContentCategory> cates = tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
//			//没有子类目设置isparent为false
//			if(cates==null||cates.size()==0){
//				TbContentCategory parent = new TbContentCategory();
//				parent.setId(cate.getParentId());
//				parent.setUpdated(date);
//				parent.setIsParent(false);
//				
//				index+=tbContentCategoryDubboServiceImpl.updCategory(parent);
//				
//				if(index==2){
//					egoResult.setStatus(200);
//				}
//			}else{
//				egoResult.setStatus(200);
//			}
//		}
		
		//实现事务回滚
		return tbContentCategoryDubboServiceImpl.del(category);
	}
}
