package domain;

import java.io.Serializable;

/**
 * 班级冲突
 * Created by xcy on 2019/5/20.
 */
public class EduClassConflict implements Serializable {

    private static final long serialVersionUID = 8310994693173303025L;

    private EduClass leftEduClass;
    private EduClass rightEduClass;
    private int conflictCount;

    public EduClassConflict(EduClass leftEduClass, EduClass rightEduClass, int conflictCount) {
        this.leftEduClass = leftEduClass;
        this.rightEduClass = rightEduClass;
        this.conflictCount = conflictCount;
    }

    public EduClass getLeftEduClass() {
        return leftEduClass;
    }

    public void setLeftEduClass(EduClass leftEduClass) {
        this.leftEduClass = leftEduClass;
    }

    public EduClass getRightEduClass() {
        return rightEduClass;
    }

    public void setRightEduClass(EduClass rightEduClass) {
        this.rightEduClass = rightEduClass;
    }

    public int getConflictCount() {
        return conflictCount;
    }

    public void setConflictCount(int conflictCount) {
        this.conflictCount = conflictCount;
    }

    @Override
    public String toString() {
        return "<" + leftEduClass + "," + rightEduClass + ">";
    }
}
