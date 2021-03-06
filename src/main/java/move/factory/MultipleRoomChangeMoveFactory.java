package move.factory;

import domain.EduClass;
import domain.LectureOfEduClass;
import domain.Room;
import domain.TimeTablingProblem;
import move.MultipleRoomChangeMove;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xcy on 2019/7/8.
 */
public class MultipleRoomChangeMoveFactory implements MoveListFactory<TimeTablingProblem> {

    @Override
    public List<? extends Move<TimeTablingProblem>> createMoveList(TimeTablingProblem timeTablingProblem) {
        List<MultipleRoomChangeMove> moveList = new ArrayList<>();
        List<LectureOfEduClass> lectures = timeTablingProblem.getLectureList();
        //过滤教室不能变动的
        Map<EduClass, List<LectureOfEduClass>> eduClasses = lectures.stream()
                .filter(lectureOfEduClass -> !lectureOfEduClass.isRoomUnmovable())
                .collect(Collectors.groupingBy(LectureOfEduClass::getEduClass, LinkedHashMap::new, Collectors.toList()));
        List<Room> roomList = timeTablingProblem.getRoomList();
        for (EduClass eduClass : eduClasses.keySet()) {
            for (Room room : roomList) {
                MultipleRoomChangeMove move = new MultipleRoomChangeMove(eduClasses.get(eduClass), room);
                moveList.add(move);
            }
        }
        return moveList;
    }

}
