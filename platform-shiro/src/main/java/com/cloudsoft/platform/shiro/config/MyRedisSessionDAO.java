package com.cloudsoft.platform.shiro.config;

import com.cloudsoft.logger.LoggerUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyRedisSessionDAO extends AbstractSessionDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyRedisSessionDAO.class);
	private RedisManager redisManager;
	private String keyPrefix = "shiro_redis_session:";

	@Override
	public void update(Session session) throws UnknownSessionException {
		LoggerUtil.info(LOGGER,"===============sessionid:{0} update===================",session.getId());
		this.saveSession(session);
	}

	private void saveSession(Session session) throws UnknownSessionException {
		LoggerUtil.info(LOGGER,"===================sessionid:{0} save==============================",session.getId());
		if (session != null && session.getId() != null) {
			byte[] key = this.getByteKey(session.getId());
			byte[] value = SerializeUtils.serialize(session);
			session.setTimeout((long) (this.redisManager.getExpire() * 1000));
			this.redisManager.set(key, value, this.redisManager.getExpire());
		} else {
			LoggerUtil.error(LOGGER,"session or session id is null");
		}
	}

	@Override
	public void delete(Session session) {
		LoggerUtil.info(LOGGER,"==========================sessionid:{0} delete===========================",session.getId());
		if (session != null && session.getId() != null) {
			this.redisManager.del(this.getByteKey(session.getId()));
		} else {
			LoggerUtil.error(LOGGER,"session or session id is null");
		}
	}

	public Collection<Session> getActiveSessions() {
		LoggerUtil.info(LOGGER,"===========================getActiveSessions==================================");
		HashSet sessions = new HashSet();
		Set keys = this.redisManager.keys(this.keyPrefix + "*");
		if (keys != null && keys.size() > 0) {
			Iterator i$ = keys.iterator();

			while (i$.hasNext()) {
				byte[] key = (byte[]) i$.next();
				Session s = (Session) SerializeUtils.deserialize(this.redisManager.get(key));
				sessions.add(s);
			}
		}

		return sessions;
	}

	protected Serializable doCreate(Session session) {
		LoggerUtil.info(LOGGER,"===================sessionid:{0} doCreate========================",session.getId());
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		LoggerUtil.info(LOGGER,"=======================sessionid:{0} doReadSession==========================",sessionId);
		if (sessionId == null) {
			LoggerUtil.error(LOGGER,"session id is null");
			return null;
		} else {
			Session s = (Session) SerializeUtils.deserialize(this.redisManager.get(this.getByteKey(sessionId)));
			return s;
		}
	}

	private byte[] getByteKey(Serializable sessionId) {
		String preKey = this.keyPrefix + sessionId;
		return preKey.getBytes();
	}

	public RedisManager getRedisManager() {
		return this.redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
		this.redisManager.init();
	}

	public String getKeyPrefix() {
		return this.keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
}