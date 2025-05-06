package com.elevate.data

import kotlinx.coroutines.flow.Flow

class ActivityRepository(private val activityDao: ActivityDao) {
    fun getActivitiesForUser(userId: String): Flow<List<ActivityEntity>> {
        return activityDao.getActivitiesForUser(userId)
    }

    suspend fun insertActivity(activity: ActivityEntity) {
        activityDao.insertActivity(activity)
    }

    suspend fun insertActivities(activities: List<ActivityEntity>) {
        activityDao.insertActivities(activities)
    }

    suspend fun updateActivity(activity: ActivityEntity) {
        activityDao.updateActivity(activity)
    }

    suspend fun deleteActivity(activity: ActivityEntity) {
        activityDao.deleteActivity(activity)
    }

    suspend fun deleteAllActivitiesForUser(userId: String) {
        activityDao.deleteAllActivitiesForUser(userId)
    }
} 