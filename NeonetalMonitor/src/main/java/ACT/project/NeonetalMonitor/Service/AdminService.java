package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.Admin;

import java.util.List;

public interface AdminService {


	public List<Admin> findByRole(String user);

	public Admin findByEmail(String user);
	
	public List<Admin> findAll();

	public void save(Admin admin);
	
}
