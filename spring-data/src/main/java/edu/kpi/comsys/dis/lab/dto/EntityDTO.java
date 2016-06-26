package edu.kpi.comsys.dis.lab.dto;

public interface EntityDTO<E> extends DTO {

    void fillFromEntity(E entity);
    void fillEntity(E entity);

}
