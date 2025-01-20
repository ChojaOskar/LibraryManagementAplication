package com.example.pjatk.librarymanagementapplication.repository;

import java.util.Set;

public class RepoUtils {

    public static Long getHighestId(RepositoryInterface repositoryInterface){
        Set<Long> keys = repositoryInterface.getDataBase().keySet();
        if(!keys.isEmpty()){
            return keys.stream().max(Long::compareTo).get();
        }
        return 1L;
    }

    public static Long getNextId(RepositoryInterface repositoryInterface){
        return getHighestId(repositoryInterface) + 1;
    }
}
