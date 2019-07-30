package ru.flexconstructor.AMZScout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.flexconstructor.AMZScout.configuration.ThrottlerProperties;
import ru.flexconstructor.AMZScout.entity.Throttler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ThrottlingService} implementation.
 */
@Service
@RequiredArgsConstructor
public class ThrottlingServiceImpl implements ThrottlingService {

    /**
     * Throttlers map {@link ConcurrentHashMap} instance.
     */
    private final ConcurrentHashMap<String, Throttler> throttlerMap = new ConcurrentHashMap<>();

    /**
     * {@link ThrottlerProperties} configuration instance.
     */
    private final ThrottlerProperties properties;

    /**
     * Checks new call.
     *
     * @param key call key (remote IP for example)
     *
     * @return check result.
     */
    @Override
    public boolean throttle(String key) {
        return this.throttlerMap.computeIfAbsent(key, newKey ->
                new Throttler(properties.getLimit(), properties.getTimeUnit())).throttle();
    }

    /**
     * Cleans map.
     */
   @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void cleanMap(){
       throttlerMap.forEach((key, throttler) ->{
           throttler.removeEldest();
           if(throttler.isEmpty()){
               throttlerMap.remove(key);
           }
       });
   }
}
