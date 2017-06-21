package com.home.hrable.file.watcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("file-watcher")
public class FileLWatcherRegister implements InitializingBean{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);
	
	@Value("${resume.directory.path}") 
	String directoryPath;
	
	public void afterPropertiesSet() throws Exception {
		
		LOGGER.info(String.format("Registered directory for files - %s",directoryPath));
		
		Path dir = Paths.get(directoryPath);
		WatchService watchService = dir.getFileSystem().newWatchService();
//		dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		
		dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		
		FileWatcher fileWatcher = new FileWatcher(watchService);
		fileWatcher.run();
		
	}
	
	
}
