import domain.Day;
import domain.EduClass;
import domain.EduClassTypeEnum;
import domain.LectureOfEduClass;
import domain.Period;
import domain.Room;
import domain.TimeTablingProblem;
import domain.TimeSlot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.BeforeClass;
import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardmediumsoft.HardMediumSoftScoreVerifier;
import util.Dataset;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcy on 2019/5/27.
 */

public class ConstraintTest {
    private static TimeTablingProblem problem;
    private HardMediumSoftScoreVerifier<TimeTablingProblem> scoreVerifier = new HardMediumSoftScoreVerifier<>(
            SolverFactory.createFromXmlResource("config/solverConfig.xml"));

    @BeforeClass
    public static void beforeClass() throws IOException {
        //读入初始数据
        problem = new TimeTablingProblem();
        Dataset.createDataset(problem,"dataset/1");
        //读入排课结果
        InputStreamReader in = new InputStreamReader(new FileInputStream("result_2019-06-11-16-24-37.csv"), "gbk");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        List<LectureOfEduClass> lectures = new ArrayList<>();
        for (CSVRecord record : records) {
            String day =record.get("period").split("_")[0];
            String timeslot = record.get("period").split("_")[1];

            Day d = new Day();
            d.setDayIndex(Integer.parseInt(day));
            TimeSlot t = new TimeSlot();
            t.setTimeSlotIndex(Integer.parseInt(timeslot));
            Period period = new Period();
            period.setDay(d);
            period.setTimeSlot(t);
            Room room = new Room();
            room.setName(record.get("room"));
            EduClass eduClass = new EduClass();
            eduClass.setName(record.get("eduClass"));

            String typeName = record.get("eduClassType");
            EduClassTypeEnum type;
            switch (typeName){
                case "行政班":
                    type = EduClassTypeEnum.ADMINISTRATIVE;
                    break;
                case "会考班":
                    type = EduClassTypeEnum.ACADEMIC;
                    break;
                case "高考班":
                    type = EduClassTypeEnum.COLLEGE;
                    break;
                case "合班":
                    type = EduClassTypeEnum.GROUP;
                    break;
                default:
                    type = null;
            }
            eduClass.setType(type);

            LectureOfEduClass lecture = new LectureOfEduClass();
            lecture.setId(Long.parseLong(record.get("id")));
            lecture.setPeriod(period);
            lecture.setRoom(room);
            lecture.setLectureIndex(Integer.parseInt(record.get("lectureIndexInCourse")));
            lecture.setEduClass(eduClass);
            lectures.add(lecture);
        }
        problem.setLectureList(lectures);
    }

    @Test
    public void conflictingLecturesDifferentCourseInSamePeriod() {
        scoreVerifier.assertHardWeight("conflictingLecturesDifferentCourseInSamePeriod", 0, problem);
    }

    @Test
    public void conflictingEduClassInSamePeriod() {
        scoreVerifier.assertHardWeight("conflictingEduClassInSamePeriod", 0, problem);
    }

    @Test
    public void roomOccupancy() {
        scoreVerifier.assertHardWeight("roomOccupancy", 0, problem);
    }

    @Test
    public void conflictingLecturesSameCourseInSamePeriod(){
        scoreVerifier.assertHardWeight("conflictingLecturesSameCourseInSamePeriod", 0, problem);
    }
}
