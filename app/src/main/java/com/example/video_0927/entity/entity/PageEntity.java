package com.example.video_0927.entity.entity;

import java.util.List;

public class PageEntity<T>{

        public int totalCount;
        public int pageSize;
        public int totalPage;
        public int currPage;
        public List<T> list;

}
