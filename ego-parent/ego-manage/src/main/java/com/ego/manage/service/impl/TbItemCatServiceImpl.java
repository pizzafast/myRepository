package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService TbItemCatDubboServiceImpl;
	@Override
	public List<EasyUiTree> showItemCat(long pid) {
		List<TbItemCat> tbItemCats = TbItemCatDubboServiceImpl.selItemCat(pid);
		List<EasyUiTree> easyUiTrees=new ArrayList<>();
		for (TbItemCat cat : tbItemCats) {
			EasyUiTree tree=new EasyUiTree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent()?"closed":"open"); 
			easyUiTrees.add(tree);
		}
		return easyUiTrees;
	}

}
