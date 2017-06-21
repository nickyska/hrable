package com.home.hrable.file.watcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatcher implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);

	private WatchService watchService;

	public FileWatcher(WatchService watchService) {
		this.watchService = watchService;
	}

	@Override
	public void run() {

		while (true) {
			WatchKey key;
			try {
				key = watchService.take();
			} catch (InterruptedException ex) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();

				System.out.println(kind.name() + ": " + fileName);

				if (kind == ENTRY_MODIFY && fileName.toString().equals("test.txt")) {
					System.out.println("My source file has changed!!!");
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}
}
