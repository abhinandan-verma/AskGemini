package com.abhinandan.askgemini.di

import android.content.Context
import com.abhinandan.askgemini.room.ChatDao
import com.abhinandan.askgemini.room.ChatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object DatabaseModule {

    @Provides
    @Singleton
    fun provideChatDao(chatDatabase: ChatDatabase): ChatDao {
        return chatDatabase.chatDao()
    }

    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return ChatDatabase.getDatabase(context = context)
    }
}