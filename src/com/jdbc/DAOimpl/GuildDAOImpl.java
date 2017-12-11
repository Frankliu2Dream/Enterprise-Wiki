package com.jdbc.DAOimpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.jdbc.DAO.GuildDAO;
import com.jdbc.bean.Guild;
import com.jdbc.tool.DBConnector;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
/*
 * author:Frank Liu
 * Date:16/11/26
 */
public class GuildDAOImpl implements GuildDAO{
	@Override
	public Guild getGuild() throws Exception{
		//初始化jdbc
		Connection conn=DBConnector.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		//初始化结果集
		Guild guild=new Guild();
		HashMap<String,ArrayList<String>> guildMap=guild.getGuildMap();
		
		
		//获得companydetail
		String sql="select * from wiki_crawl.companydetail;";
		
		pstmt=(PreparedStatement)conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		
		while(rs.next()){
			String COMPANY_ABBR=rs.getString(2);
			String CSRC_CODE_DESC=rs.getString(6);
			if(!guildMap.containsKey(CSRC_CODE_DESC)){
				ArrayList<String> temp_COMPANY_ABBRList=new ArrayList<String>();
				temp_COMPANY_ABBRList.add(COMPANY_ABBR);
				guildMap.put(CSRC_CODE_DESC, temp_COMPANY_ABBRList);
			}
			else{
				ArrayList<String> temp_companyList=guildMap.get(CSRC_CODE_DESC);
				temp_companyList.add(COMPANY_ABBR);
			}
		}
		pstmt.close();
		conn.close();
		return guild;
	}
}
