/*
 *
 *  * Copyright 2014 Orient Technologies.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.orientechnologies.lucene.query;

import com.orientechnologies.orient.core.command.OCommandContext;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/**
 * Created by Enrico Risa on 08/01/15.
 */
public class QueryContext {

    public final OCommandContext context;
    public final IndexSearcher searcher;
    public final Query query;
    public final Filter filter;
    public final Sort sort;
    public QueryContextCFG cfg;

    public QueryContext(OCommandContext context, IndexSearcher searcher, Query query) {
        this(context, searcher, query, null, null);
    }

    public QueryContext(OCommandContext context, IndexSearcher searcher, Query query, Filter filter) {
        this(context, searcher, query, filter, null);
    }

    public QueryContext(OCommandContext context, IndexSearcher searcher, Query query, Filter filter, Sort sort) {
        this.context = context;
        this.searcher = searcher;
        this.query = query;
        this.filter = filter;
        this.sort = sort;
        initCFG();
    }

    private void initCFG() {
        if (filter != null && sort != null) {
            cfg = QueryContextCFG.FILTER_SORT;
        } else if (filter == null && sort == null) {
            cfg = QueryContextCFG.NO_FILTER_NO_SORT;
        } else if (filter != null) {
            cfg = QueryContextCFG.FILTER;
        } else {
            cfg = QueryContextCFG.SORT;
        }
    }


    public enum QueryContextCFG {
        NO_FILTER_NO_SORT, FILTER_SORT, FILTER, SORT
    }
}
