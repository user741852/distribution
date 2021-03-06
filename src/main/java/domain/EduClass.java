package domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by xcy on 2019/5/20.
 */
public class EduClass implements Serializable,Comparable<EduClass> {
    private static final long serialVersionUID = 2490355394270166676L;

    @PlanningId
    private Long id;
    private String name;
    private List<Student> students;
    private String subjectName;

    private EduClassTypeEnum type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EduClassTypeEnum getType() {
        return type;
    }

    public void setType(EduClassTypeEnum type) {
        this.type = type;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EduClass eduClass = (EduClass) o;
        return type == eduClass.type &&
                Objects.equals(name, eduClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public int compareTo(EduClass o) {
        return new CompareToBuilder()
                .append(name,o.name)
                .append(type,o.type)
                .toComparison();
    }

}
