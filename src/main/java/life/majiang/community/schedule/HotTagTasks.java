package life.majiang.community.schedule;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 0 1 * * *")
    public void HotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("HotTagSchedule start {}", new Date());
        List<Question> questionList = new ArrayList<>();
        while (offset == 0 || questionList.size() == limit) {
            questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : questionList) {
                log.info("List question : {}", question.getId());
            }
            offset += limit;
        }
        log.info("HotTagSchedule stop {}", new Date());
    }
}
