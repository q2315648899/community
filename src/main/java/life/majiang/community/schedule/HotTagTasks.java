package life.majiang.community.schedule;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Component注释默认是单例的
 * hottag热门标签计算算法:
 * 1,当前标签的问题数 java 3,js 2
 * 2,每一个问题的评论数 Java 4，js 10
 * priority = 5 * question count + comments
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 0 1 * * *")
    public void HotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("HotTagSchedule start {}", new Date());
        List<Question> questionList = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || questionList.size() == limit) {
            questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : questionList) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }

        hotTagCache.updateTags(priorities);
        log.info("HotTagSchedule stop {}", new Date());
    }
}
