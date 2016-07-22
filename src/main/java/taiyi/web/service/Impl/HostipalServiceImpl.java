/**
 * 
 */
package taiyi.web.service.Impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taiyi.web.dao.HostipalMapper;
import taiyi.web.model.Hostipal;
import taiyi.web.service.HostipalService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年7月5日
 */
@Service
public class HostipalServiceImpl implements HostipalService{
	
	@Autowired
	private HostipalMapper hostipalMapper;

	/* 
	 * @see taiyi.web.service.HostipalService#deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return hostipalMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#insert(taiyi.web.model.Hostipal)
	 */
	@Override
	public int insert(Hostipal record) {
		return hostipalMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#insertSelective(taiyi.web.model.Hostipal)
	 */
	@Override
	public int insertSelective(Hostipal record) {
		return hostipalMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public Hostipal selectByPrimaryKey(Integer id) {
		return hostipalMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#updateByPrimaryKeySelective(taiyi.web.model.Hostipal)
	 */
	@Override
	public int updateByPrimaryKeySelective(Hostipal record) {
		return hostipalMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#updateByPrimaryKey(taiyi.web.model.Hostipal)
	 */
	@Override
	public int updateByPrimaryKey(Hostipal record) {
		return hostipalMapper.updateByPrimaryKey(record)
				;
	}

	/* 
	 * @see taiyi.web.service.HostipalService#selectAll()
	 */
	@Override
	public List<Hostipal> selectAll() {
		return hostipalMapper.selectAll();
	}

	/* 
	 * @see taiyi.web.service.HostipalService#selectByName(java.lang.String)
	 */
	@Override
	public Hostipal selectByName(String name) {
		return hostipalMapper.selectByName(name);
	}

	/* 
	 * @see taiyi.web.service.HostipalService#selectByMacAddress(java.lang.String)
	 */
	@Override
	public Hostipal selectByMacAddress(String macAddress) {
		return hostipalMapper.selectByMacAddress(macAddress);
	}

}
