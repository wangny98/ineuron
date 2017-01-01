package com.ineuron.dataaccess.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ineuron.common.exception.RepositoryException;

public class INeuronRepository {
	
	public void add(String mapperName,Object obj) throws RepositoryException{
		
		SqlSession session = null;
		try{
			session = INeuronDBConnection.getSession();
			if(obj != null){
				session.insert(mapperName, obj);
				session.commit();
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute insert sql: " + mapperName + "!", e);
		} catch (RepositoryException e) {
			throw new RepositoryException("failed to create SqlSession!", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
	}
	
	public void update(String mapperName,Object obj) throws RepositoryException{
		
		SqlSession session = null;
		try{
			session = INeuronDBConnection.getSession();
			if(obj != null){
				session.update(mapperName, obj);
				session.commit();
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute update sql: " + mapperName + "!", e);
		} catch (RepositoryException e) {
			throw new RepositoryException("failed to create SqlSession!", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
	}
	
	public void delete(String mapperName,Object obj) throws RepositoryException{
		
		SqlSession session = null;
		try{
			session = INeuronDBConnection.getSession();
			if(obj != null){
				session.delete(mapperName, obj);
				session.commit();
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute delete sql: " + mapperName + "!", e);
		} catch (RepositoryException e) {
			throw new RepositoryException("failed to create SqlSession!", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
	}
	
	public <E> List<E>  select(String mapperName, Object obj) throws RepositoryException{
		
		SqlSession session = null;
		try{
			session = INeuronDBConnection.getSession();
			if(obj != null){
				return session.selectList(mapperName, obj);
			}else{
				return session.selectList(mapperName);
			}
			
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute select sql: " + mapperName + "!", e);
		} catch (RepositoryException e) {
			throw new RepositoryException("failed to create SqlSession!", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		
	}

	public <T> T  selectOne(String mapperName, Object obj) throws RepositoryException {
		SqlSession session = null;
		try{
			session = INeuronDBConnection.getSession();
			return session.selectOne(mapperName, obj);
		} catch(RuntimeException e){
			throw new RepositoryException("failed to excute select sql: " + mapperName + "!", e);
		} catch (RepositoryException e) {
			throw new RepositoryException("failed to create SqlSession!", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
	}

}
