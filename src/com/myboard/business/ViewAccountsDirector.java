package com.myboard.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myboard.dao.AccountPermissionsDao;
import com.myboard.dao.DepartmentDao;
import com.myboard.dao.Users;
import com.myboard.dao.UsersDao;

public class ViewAccountsDirector implements Serializable {

	private static final long serialVersionUID = -1836299781031398668L;
	private List<Users> users;
	
	public ViewAccountsDirector()
	{
		this.setUsers(getAllUsers());
	}
	
	
	public List<Users> getAllUsers()
	{
		UsersDao dao = new UsersDao();
		
		return dao.readAll();
	}
	//filters users by teachers
	public ArrayList<Users> getTeachers()
	{
		ArrayList<Users> teachers = new ArrayList<Users>();
		
		for(Users user : this.getAllUsers())
		{
			if(user.getPermission().getPermissionId() == 2) //faculty
			{
				teachers.add(user);
			}
		}
		
		return teachers;
	}
	//filters users by students
	public ArrayList<Users> getStudents()
	{
		ArrayList<Users> students = new ArrayList<Users>();
		
		for(Users user : this.getAllUsers())
		{
			if(user.getPermission().getPermissionId() == 3)
			{
				students.add(user);
			}
		}
		
		return students;
	}
	//filters users by admins
	public ArrayList<Users> getAdmins()
	{
		ArrayList<Users> admins = new ArrayList<Users>();
		
		for(Users user : this.getAllUsers())
		{
			if(user.getPermission().getPermissionId() == 1 || user.getPermission().getPermissionId() == 0)
			{
				admins.add(user);
			}
		}
		
		return admins;
	}
	
	public List<Users> getUsers() {
		return users;
	}


	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
	
	
}
