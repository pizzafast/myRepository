package com.ego.dubbo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService{
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TbContentCategory> selByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		//查找出状态为1的内容类目   状态为1表示正常  2表示删除
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insCategory(TbContentCategory category,TbContentCategory categoryParent) throws Exception {
		int index=0;
		//插入新增  进行事务回滚  事物的四大特性
		try {
			index+=tbContentCategoryMapper.insertSelective(category);
			//将新增的父类目的isparent改为父
			index+=tbContentCategoryMapper.updateByPrimaryKeySelective(categoryParent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==2){
			return 1;
		}else{
			throw new Exception("新增失败:原因可能是当前类目不存在！");
		}
	}

	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int del(TbContentCategory category) throws Exception {
		
		//实现了事务回滚
		int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
		if(index>0){
			TbContentCategory cate = tbContentCategoryMapper.selectByPrimaryKey(category.getId());
			List<TbContentCategory> cates = selByPid(cate.getParentId());
			//没有子类目设置isparent为false
			if(cates==null||cates.size()==0){
				TbContentCategory parent = new TbContentCategory();
				parent.setId(cate.getParentId());
				parent.setUpdated(category.getUpdated());
				parent.setIsParent(false);
				
				index+=tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
				
				if(index==2){
					return 1;
				}else {
					throw new Exception("父类目可能不存在");
				}
			}else{
				return 1;
			}
		}
		return index;
	}

	@Override
	public int updCategory(TbContentCategory category) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
	}
}
