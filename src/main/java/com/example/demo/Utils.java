package com.example.demo;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

public class Utils {
	static public String pathWithPage(String base, Pageable pageable, Object... args) {
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path(base);
		for (int i = 0; i < args.length; i += 2) {
			if (i >= args.length - 1) break;
			builder.queryParam((String) args[i], args[i + 1]);
		}
		builder.queryParam("page", pageable.getPageNumber());
		URI url = builder.build().encode().toUri();
		return url.toString();

	}

	static public String pathWithSort(String base, Pageable pageable, Object... args) {
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path(base);
		for (int i = 0; i < args.length; i += 2) {
			if (i >= args.length - 1) break;
			builder.queryParam((String) args[i], args[i + 1]);
		}
		pageable.getSort().forEach(o -> {
			builder.queryParam("sort", o.getProperty() + "," + o.getDirection());
		});
		URI url = builder.build().encode().toUri();
		return url.toString();
	}
}
