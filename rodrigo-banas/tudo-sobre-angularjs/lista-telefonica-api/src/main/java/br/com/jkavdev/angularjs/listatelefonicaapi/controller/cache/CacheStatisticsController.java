package br.com.jkavdev.angularjs.listatelefonicaapi.controller.cache;

import net.sf.ehcache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cache")
public class CacheStatisticsController {

    @GetMapping("/stats")
    public List<CacheStatisticDTO> cacheStats() throws Exception {
        CacheManager manager = CacheManager.getInstance();

        return Stream.of(manager.getCacheNames())
                .map(name -> CacheStatisticDTO.from(manager.getCache(name).getStatistics()))
                .collect(Collectors.toList());
    }

}
