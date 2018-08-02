package ldt.springframework.springmvc.services.mapservice;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/16/18
 */


import ldt.springframework.springmvc.domain.DomainObject;

import java.util.*;

public abstract class AbstractMapService {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    protected Map<Integer, DomainObject> domainMap;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public AbstractMapService(){
        this.domainMap = new HashMap<>();
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    public List<DomainObject> listAll(){
        return new ArrayList<>(domainMap.values());
    }

    public DomainObject getById(Integer id){
        return this.domainMap.get(id);
    }

    public DomainObject saveOrUpdateDomainObject(DomainObject domainObject){
        if(domainObject != null){
            // generate new course's key
            if(domainObject.getId() == null){
                domainObject.setId(this.genNextKey());
            }

            // save to list
            this.domainMap.put(domainObject.getId(), domainObject);
            return domainObject;
        }else{
            throw new RuntimeException("Object can not be null!");
        }
    }

    public void deleteDomainObject(Integer id){
        if(id != null){
            this.domainMap.remove(id);
        }else{
            throw new RuntimeException("The Object's Id can not be null!");
        }
    }

    private int genNextKey(){
        if(this.domainMap.values().isEmpty())
            return 1;
        return Collections.max(this.domainMap.keySet()) + 1;
    }
}
