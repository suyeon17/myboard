package com.myboard.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myboard.exception.EntityAlreadyExistsException;
import com.myboard.exception.EntityNotFoundException;

public class AdditionalCalendarEntriesDao extends BaseDao {

	private static final Log log = LogFactory.getLog(AdditionalCalendarEntriesDao.class);
	private static final String entityFullName = AdditionalCalendarEntries.class.getName();
	private static final String entitySimpleName = AdditionalCalendarEntries.class.getSimpleName();

	public void create(AdditionalCalendarEntries transientInstance) {
		log.debug("creating " + entitySimpleName + " instance");
		
		try {
			super.create(transientInstance);
			log.debug("creating " + entitySimpleName + " successful");
		} catch(EntityAlreadyExistsException ex){
			log.debug("creating " + entitySimpleName + " failed: " + ex.getMessage());
		} catch (RuntimeException re) {
			log.error("creating " + entitySimpleName + " failed", re);
			throw re;
		}
	}
	
	public AdditionalCalendarEntries read(String id) {
		log.debug("reading " + entitySimpleName + " instance with id: " + id);
		AdditionalCalendarEntries instance = null;
		
		try {
			instance = (AdditionalCalendarEntries)super.read(entityFullName, id);
			log.debug(entitySimpleName + " read successful, instance found");
		} catch(EntityNotFoundException ex){
			log.error(entitySimpleName + " read successful, no instance found", ex);
		} catch (RuntimeException re) {
			log.error(entitySimpleName + " read failed", re);
			throw re;
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdditionalCalendarEntries> readAll(AdditionalCalendarEntries instance) {
		log.debug("readAll " + entitySimpleName);
		List<AdditionalCalendarEntries> result = null;
			
		try {
			result = (List<AdditionalCalendarEntries>)super.readAll(entityFullName, instance);
			log.debug(entitySimpleName + " readAll successful, instance found");
		} catch(EntityNotFoundException ex){
			log.error(entitySimpleName + " readAll successful, no instance found", ex);
		} catch (RuntimeException re) {
			log.error(entitySimpleName + " readAll failed", re);
			throw re;
		}
		return result;
	}

	public void update(AdditionalCalendarEntries instance) {
		log.debug("updating " + entitySimpleName + " instance");

		try {
			super.update(instance);
			log.debug(entitySimpleName + " update successful");
		} catch (RuntimeException re) {
			log.error(entitySimpleName + " update failed", re);
			throw re;
		}
	}

	public void delete(AdditionalCalendarEntries persistentInstance) {
		log.debug("deleting " + entitySimpleName + " instance");
		
		try {
			super.delete(persistentInstance);
			log.debug(entitySimpleName + " delete successful");
		} catch (RuntimeException re) {
			log.error(entitySimpleName + " delete failed", re);
			throw re;
		}
	}
}
