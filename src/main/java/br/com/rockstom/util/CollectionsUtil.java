package br.com.rockstom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class CollectionsUtil {
	
	private CollectionsUtil() {}
	
	public static <T> List<List<T>> partition(Collection<T> list, int qntPages) {
	    final int size = list.size();
	    
	    int perPage = size / qntPages;
	    
	    if (size % qntPages > 0) {
	    	perPage++;
	    }
	    
	    final int qntPerPage = perPage;
	    final AtomicInteger counter = new AtomicInteger(0);

        final Collection<List<T>> partitioned = list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / qntPerPage))
                .values();
	    
	    return new ArrayList<>(partitioned);
	}
	
	public static boolean nonEmpty(Collection<?> list) {
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isEmpty(Object[] list) {
		return list == null || list.length == 0 || list[0] == null;
	}
}
