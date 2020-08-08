# 1 省网
## 1.1 月报数据SQL汇总
### 1.1.1 各地疑似人员走访跟进情况
```sql
WITH temp_deject AS (
	SELECT
		a.*,
	CASE
        WHEN a.score < 40 THEN 1 
			WHEN a.score >= 40 
			AND a.score < 60 THEN 2 
				WHEN a.score >= 60 
				AND a.score < 90 THEN 3 
					WHEN a.score >= 90 THEN 4 
				END AS dejected_level 
			FROM
				(
				SELECT
					dp.id,
					dp.area_code,
					dp.years,
					dp.status,
					dp.name,
					dp.id_card_num,
					dp.time_created,
					dp.time_modified,
					(
					SELECT
						listagg ( dl.tag_id, ',' ) within GROUP ( ORDER BY tag_id ) 
					FROM
						gsmp_dejected_label dl
						INNER JOIN GSMP_QUOTA_DEJECTED_INFO qdi ON qdi.id = dl.tag_id 
						AND qdi.quota_type != 2 
					WHERE
						dl.dejected_id = dp.id 
					) AS tagnum,
					dp.birthday,
					dp.ethnicity,
					dp.marital_status,
					dp.house_addr,
					dp.photo_url,
					dp.sex,
					dp.remark,
					dp.education_code,
					dp.residence_address,
					(
					SELECT
						SUM( dl.QUOTA_SCORE ) 
					FROM
						GSMP_DEJECTED_LABEL dl 
					WHERE
						dl.DEJECTED_ID = DP.ID 
						AND NOT EXISTS ( SELECT DEJECTED_ID FROM GSMP_DEJECTED_LABEL L WHERE DP.ID = L.DEJECTED_ID AND QUOTA_TYPE = 3 ) 
					) AS score 
				FROM
					gsmp_dejected_people dp 
				WHERE
					1 = 1 
				) a 
			) SELECT
			--  lg.code AS areacode,
			substr( lg.name, 0, 2 ) AS areaname,
			dp2."风险一般当月",
			dp."风险一般累计",
			dp2."风险较高当月",
			dp."风险较高累计",
			dp2."风险高当月",
			dp."风险高累计",
			dp2."风险极高当月",
			dp."风险极高累计",
			dp5.fxjgystotal AS 风险较高及以上总人数
			,
			dp4.ypc AS 已跟进排除
			,
			trunc( dp4.ypc / dp5.fxjgystotal, 4 ) AS 已跟进排除率
			,
			dp3.wpc AS 未跟进排除
			,
			trunc( dp3.wpc / dp5.fxjgystotal, 4 ) AS 未跟进排除率 
		FROM
			gsmp_loc_grids lg
			LEFT JOIN (
			SELECT
				* 
			FROM
				(
				SELECT
					substr( area_code, 1, 4 ) AS areacode,
					dejected_level,
					count( 1 ) value 
				FROM
					temp_deject t 
				WHERE
					1 = 1 
					AND area_code LIKE '35%' 
					AND t.years <= 202006                            -------------哪个月跑设置为哪个月或者设置为当月之后的某月
				GROUP BY
					substr( area_code, 1, 4 ),
					dejected_level 
				) pivot ( max( value ) FOR dejected_level IN ( '1' AS 风险一般累计, '2' AS 风险较高累计, '3' AS 风险高累计, '4' AS 风险极高累计 ) ) 
			) dp ON lg.code = dp.areacode
			LEFT JOIN (
			SELECT
				* 
			FROM
				(
				SELECT
					substr( area_code, 1, 4 ) AS areacode,
					dejected_level,
					count( 1 ) value 
				FROM
					temp_deject t 
				WHERE
					1 = 1 
					AND area_code LIKE '35%' 
					AND t.years = 202005                              -------------设置要统计的数据的月份
				GROUP BY
					substr( area_code, 1, 4 ),
					dejected_level 
				) pivot ( max( value ) FOR dejected_level IN ( '1' AS 风险一般当月, '2' AS 风险较高当月, '3' AS 风险高当月, '4' AS 风险极高当月 ) ) 
			) dp2 ON lg.code = dp2.areacode
			LEFT JOIN (
			SELECT
				substr( area_code, 1, 4 ) AS areacode,
				count( 1 ) wpc 
			FROM
				temp_deject t 
			WHERE
				1 = 1 
				AND area_code LIKE '35%' 
				AND dejected_level >= 2                     -------------风险较高及以上
				AND status = 0                              -------------未排查
				AND t.years <= 202006                       -------------哪个月跑设置为哪个月或者设置为当月之后的某月
			GROUP BY
				substr( area_code, 1, 4 ) 
			) dp3 ON lg.code = dp3.areacode
			LEFT JOIN (
			SELECT
				substr( area_code, 1, 4 ) AS areacode,
				count( 1 ) ypc 
			FROM
				temp_deject t 
			WHERE
				1 = 1 
				AND area_code LIKE '35%' 
				AND dejected_level >= 2                    -------------风险较高及以上
				AND t.years <= 202006                      -------------哪个月跑设置为哪个月或者设置为当月之后的某月
				AND status >= 1                            -------------已排查
			GROUP BY
				substr( area_code, 1, 4 ) 
			) dp4 ON lg.code = dp4.areacode
			LEFT JOIN (
			SELECT
				substr( area_code, 1, 4 ) AS areacode,
				count( 1 ) fxjgystotal 
			FROM
				temp_deject t 
			WHERE
				1 = 1 
				AND area_code LIKE '35%' 
				AND dejected_level >= 2                             -------------风险较高及以上
				AND t.years <= 202006                               -------------哪个月跑设置为哪个月或者设置为当月之后的某月
			GROUP BY
				substr( area_code, 1, 4 ) 
			) dp5 ON lg.code = dp5.areacode 
		WHERE
			1 = 1 
			AND lg.code LIKE '35%' 
			AND length( lg.code ) = 4 
	ORDER BY
	lg.code
```
### 1.1.2 疑似人员标签统计
```sql
WITH temp_deject AS (
	SELECT
		dp.id,
		dp.area_code,
		dp.years,
		dp.status,
		dp.name,
		dp.id_card_num,
		dp.time_created,
		dp.time_modified,
		(
		SELECT
			listagg ( dl.tag_id, ',' ) within GROUP ( ORDER BY tag_id ) 
		FROM
			gsmp_dejected_label dl
			INNER JOIN GSMP_QUOTA_DEJECTED_INFO qdi ON qdi.id = dl.tag_id 
			AND qdi.quota_type != 2 
		WHERE
			dl.dejected_id = dp.id 
		) AS tagnum,
		dp.birthday,
		dp.ethnicity,
		dp.marital_status,
		dp.house_addr,
		dp.photo_url,
		dp.sex,
		dp.remark,
		dp.dejected_level,
		dp.education_code,
		dp.residence_address,
		(
		SELECT
			SUM( dl.QUOTA_SCORE ) 
		FROM
			GSMP_DEJECTED_LABEL dl 
		WHERE
			dl.DEJECTED_ID = DP.ID 
			AND NOT EXISTS ( SELECT DEJECTED_ID FROM GSMP_DEJECTED_LABEL L WHERE DP.ID = L.DEJECTED_ID AND QUOTA_TYPE = 3 ) 
		) AS score 
	FROM
		gsmp_dejected_people dp 
	WHERE
		1 = 1 
	) SELECT
	tagname AS 标签名,
	current_count AS 本月,
	total_count AS 累计 
FROM
	(
	SELECT
		dl.tag_id AS tagid,
		dl.tag_name AS tagname,
		count( dp.id ) AS total_count,
		count( dp2.id ) AS current_count 
	FROM
		gsmp_dejected_label dl
		LEFT JOIN temp_deject dp ON dp.id = dl.dejected_id 
		AND dp.YEARS <= 202006               -----------------哪个月跑设置为哪个月或者设置为当月之后的某月
-- 		AND dp.YEARS >= 202001 
		AND dp.area_code LIKE '35%' 
		LEFT JOIN temp_deject dp2 ON dp2.id = dl.dejected_id 
		AND dp2.YEARS = 202005               -----------------设置要统计的数据的月份
		AND dp2.area_code LIKE '35%'  
	WHERE
		1 = 1 
		AND is_valid = 1 
	GROUP BY
		dl.tag_id,
		dl.tag_name 
	ORDER BY
	dl.tag_id 
	)
```
### 1.1.3 近6月全省风险指数得分趋势
```sql
SELECT
	mon.monthlist 年月,
	round( ( nvl( r.score, 0 ) ) / 10, 2 ) 得分,
	nvl( r.warn, 0 ) 预警数量 
FROM
	(
	SELECT
		TO_CHAR( ADD_MONTHS( add_months( to_date( '202004', 'yyyymm' ),- 6 ), ROWNUM ), 'yyyyMM' ) AS monthlist    ---------- 要统计哪个月的就改成哪个月的，和web时间选择一样
	FROM
		DUAL CONNECT BY ROWNUM <= months_between( add_months( to_date( '202004', 'yyyymm' ),- 1 ), add_months( to_date( '202004', 'yyyymm' ),- 6 ) ) + 1 
	) mon
	LEFT JOIN (
	SELECT
		p.years,
		sum( p.score ) score,
		sum( p.warn ) warn 
	FROM
		(
		SELECT
			k.quota_name,
			k.area_code,
			k.years,
		CASE
				
				WHEN k.area_code = 35 THEN
				k.warn ELSE 0 
			END warn,
CASE
	
	WHEN k.area_code != 35 THEN
	nvl( k.result_score, 0 ) ELSE 0 
	END score 
FROM
	(
	SELECT
		* 
	FROM
		(
		SELECT
			ROW_NUMBER ( ) OVER ( PARTITION BY r3.quota_id, r3.area_code, r3.years ORDER BY r3.quota_id DESC ) AS ROWR,
			t.quota_name,
			r3.*,
		CASE
				
				WHEN lg.lvl = 1 THEN
				(
				CASE
						
						WHEN w.warn_type = 01 THEN
						( CASE WHEN r3.quota_result_value > w.province_value THEN 1 ELSE 0 END ) 
							WHEN w.warn_type = 02 THEN
							( CASE WHEN r3.quota_result_value > w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
								WHEN w.warn_type = 03 THEN
								( CASE WHEN r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
									WHEN w.warn_type = 04 THEN
									( CASE WHEN r3.quota_result_value < w.province_value THEN 1 ELSE 0 END ) 
										WHEN w.warn_type = 05 THEN
										( CASE WHEN r3.quota_result_value < w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
										END 
										) 
										WHEN lg.lvl = 2 THEN
										(
										CASE
												
												WHEN w.warn_type = 01 THEN
												( CASE WHEN r3.quota_result_value > w.city_value THEN 1 ELSE 0 END ) 
													WHEN w.warn_type = 02 THEN
													( CASE WHEN r3.quota_result_value > w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
														WHEN w.warn_type = 03 THEN
														( CASE WHEN r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
															WHEN w.warn_type = 04 THEN
															( CASE WHEN r3.quota_result_value < w.city_value THEN 1 ELSE 0 END ) 
																WHEN w.warn_type = 05 THEN
																( CASE WHEN r3.quota_result_value < w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
																END 
																) 
																WHEN lg.lvl = 3 THEN
																(
																CASE
																		
																		WHEN w.warn_type = 01 THEN
																		( CASE WHEN r3.quota_result_value > w.county_value THEN 1 ELSE 0 END ) 
																			WHEN w.warn_type = 02 THEN
																			( CASE WHEN r3.quota_result_value > w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																				WHEN w.warn_type = 03 THEN
																				( CASE WHEN r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																					WHEN w.warn_type = 04 THEN
																					( CASE WHEN r3.quota_result_value < w.county_value THEN 1 ELSE 0 END ) 
																						WHEN w.warn_type = 05 THEN
																						( CASE WHEN r3.quota_result_value < w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																						END 
																						) 
																					END warn 
																	FROM
																		GSMP_QUOTA_SCORE_RESULT r3
																		LEFT JOIN gsmp_quota_info t ON t.id = r3.quota_id
																		LEFT JOIN gsmp_quota_warn_set w ON r3.quota_id = w.quota_id
																		LEFT JOIN gsmp_loc_grids lg ON r3.area_code = lg.code 
																	WHERE
																		1 = 1 
																		AND lg.code LIKE '35%' 
																		AND lg.lvl <= 2 
																	) N 
																WHERE
																	N.ROWR = 1 
																) k 
															) p 
														GROUP BY
															p.years 
														) r ON r.years = mon.monthlist 
													ORDER BY
													mon.monthlist,
	warn DESC
```
### 1.1.4 社会治安风险指数
```sql
 select area_name as 地市, score as 得分, rank as 排名, warn as 预警项数量, content_warn as 预警项 from (
 select a.*,rank() over(order by a.score desc) rank from(  
select  p.code,substr(p.name, 0, 2) area_name,round(nvl(sum(p.score),0),2) as score,   
 nvl(sum(p.warn),0) warn, listagg(p.content_warn,';')within group(order by score) content_warn  from  (select p1.*,p2.content_warn, lg.code,lg.name,lg.lvl from GSMP_LOC_GRIDS lg 
 left join  ( select k.quota_name, k.area_code,k.result_score score,    
  case when k.lvl=1 then  
    (case when k.warn_type=01 then (case when k.quota_result_value > k.province_value  
then 1 else 0 end) 
          when k.warn_type=02 then (case when k.quota_result_value > k.province_value  
             or k.quota_result_value = k.province_value then 1 else 0 end) 
          when k.warn_type=03 then (case when k.quota_result_value = k.province_value  
then 1 else 0 end) 
          when k.warn_type=04 then (case when k.quota_result_value < k.province_value  
then 1 else 0 end) 
          when k.warn_type=05 then (case when k.quota_result_value < k.province_value  
             or k.quota_result_value = k.province_value then 1 else 0 end) 
     end ) 
     when k.lvl = 2 then  
       (case when k.warn_type=01 then (case when k.quota_result_value > k.city_value  
then 1 else 0 end) 
          when k.warn_type=02 then (case when k.quota_result_value > k.city_value  
             or k.quota_result_value = k.city_value then 1 else 0 end) 
          when k.warn_type=03 then (case when k.quota_result_value = k.city_value then  
1 else 0 end) 
          when k.warn_type=04 then (case when k.quota_result_value < k.city_value then  
1 else 0 end) 
          when k.warn_type=05 then (case when k.quota_result_value < k.city_value  
             or k.quota_result_value = k.city_value then 1 else 0 end) 
        end ) 
     when k.lvl = 3 then  
       (case when k.warn_type=01 then (case when k.quota_result_value > k.county_value  
then 1 else 0 end) 
          when k.warn_type=02 then (case when k.quota_result_value > k.county_value  
             or k.quota_result_value = k.county_value then 1 else 0 end) 
          when k.warn_type=03 then (case when k.quota_result_value = k.county_value  
then 1 else 0 end) 
          when k.warn_type=04 then (case when k.quota_result_value < k.county_value  
then 1 else 0 end) 
          when k.warn_type=05 then (case when k.quota_result_value < k.county_value  
             or k.quota_result_value = k.county_value then 1 else 0 end) 
        end ) 
 end warn
  
 from( select N.*
 
  from (SELECT ROW_NUMBER()OVER(PARTITION BY r3.quota_id,r3.area_code 
 ORDER BY r3.quota_id  DESC ) AS ROWR,t.quota_name, t.weight_type, r3.*,w.province_value,   
 w.city_value,w.county_value,lg.lvl,w.warn_type 
  ,
 case when lg.lvl=1 then  
    (case when w.warn_type=01 then (case when r3.quota_result_value > w.province_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.province_value  
             or r3.quota_result_value = w.province_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.province_value  
then 1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.province_value  
then 1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.province_value  
             or r3.quota_result_value = w.province_value then 1 else 0 end) 
     end ) 
     when lg.lvl = 2 then  
       (case when w.warn_type=01 then (case when r3.quota_result_value > w.city_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.city_value  
             or r3.quota_result_value = w.city_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.city_value then  
1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.city_value then  
1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.city_value  
             or r3.quota_result_value = w.city_value then 1 else 0 end) 
        end ) 
     when lg.lvl = 3 then  
       (case when w.warn_type=01 then (case when r3.quota_result_value > w.county_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.county_value  
             or r3.quota_result_value = w.county_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.county_value  
then 1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.county_value  
then 1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.county_value  
             or r3.quota_result_value = w.county_value then 1 else 0 end) 
        end ) 
 end warn
 from  GSMP_QUOTA_SCORE_RESULT r3     
 left join gsmp_quota_info t on t.id = r3.quota_id     
  left join gsmp_quota_warn_set w on  r3.quota_id=w.quota_id   
 left join gsmp_loc_grids lg on r3.area_code = lg.code where 1=1  
  and r3.years = '202004'                                                                 -----------------------------------------修改时间为需要统计的月份
 ) N WHERE  N.ROWR = 1 
  ) k 
   ) p1 on lg.code = p1.area_code 
    
    
    left join  ( select distinct k.quota_name, k.area_code,k.result_score score,    
  case when k.weight_type = 1 then    
 k.quota_name || '为' || decode(substr(k.quota_result_value,1,1),'.','0'||   
 k.quota_result_value,k.quota_result_value) ||',' || case when k.warn_type = 01 then  
'大于' 
 when k.warn_type = 01 then '大于等于'  when k.warn_type = 01 then '等于'  
    when k.warn_type = 01 then '小于' when k.warn_type = 01 then '小于等于' end || '预警 
值' ||      
 decode(substr(k.city_value,1,1),'.','0'|| k.city_value,k.city_value )   
 else    
 k.quota_name || '为' || decode(substr(k.quota_result_value,1,1),'.','0'||   
 k.quota_result_value,k.quota_result_value) ||'%,'||case when k.warn_type = 01 then  
'大于' 
 when k.warn_type = 01 then '大于等于'  when k.warn_type = 01 then '等于'  
    when k.warn_type = 01 then '小于' when k.warn_type = 01 then '小于等于' end ||'预警值' 
 || decode(substr  
(k.city_value,1,1),'.','0'||   
 k.city_value,k.city_value )||'%' end content_warn    
 from( select N.*
 
  from (SELECT ROW_NUMBER()OVER(PARTITION BY r3.quota_id,r3.area_code 
 ORDER BY r3.quota_id  DESC ) AS ROWR,t.quota_name, t.weight_type, r3.*,w.province_value,   
 w.city_value,w.county_value,lg.lvl,w.warn_type 
  ,
 case when lg.lvl=1 then  
    (case when w.warn_type=01 then (case when r3.quota_result_value > w.province_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.province_value  
             or r3.quota_result_value = w.province_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.province_value  
then 1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.province_value  
then 1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.province_value  
             or r3.quota_result_value = w.province_value then 1 else 0 end) 
     end ) 
     when lg.lvl = 2 then  
       (case when w.warn_type=01 then (case when r3.quota_result_value > w.city_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.city_value  
             or r3.quota_result_value = w.city_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.city_value then  
1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.city_value then  
1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.city_value  
             or r3.quota_result_value = w.city_value then 1 else 0 end) 
        end ) 
     when lg.lvl = 3 then  
       (case when w.warn_type=01 then (case when r3.quota_result_value > w.county_value  
then 1 else 0 end) 
          when w.warn_type=02 then (case when r3.quota_result_value > w.county_value  
             or r3.quota_result_value = w.county_value then 1 else 0 end) 
          when w.warn_type=03 then (case when r3.quota_result_value = w.county_value  
then 1 else 0 end) 
          when w.warn_type=04 then (case when r3.quota_result_value < w.county_value  
then 1 else 0 end) 
          when w.warn_type=05 then (case when r3.quota_result_value < w.county_value  
             or r3.quota_result_value = w.county_value then 1 else 0 end) 
        end ) 
 end warn
 from  GSMP_QUOTA_SCORE_RESULT r3     
 left join gsmp_quota_info t on t.id = r3.quota_id     
  left join gsmp_quota_warn_set w on  r3.quota_id=w.quota_id   
 left join gsmp_loc_grids lg on r3.area_code = lg.code where 1=1  
 and r3.years = '202004'                                                                     -----------------------------------------修改时间为需要统计的月份
 ) N WHERE  N.ROWR = 1 
  ) k 
  where k.warn = 1
   ) p2 on lg.code = p2.area_code and p1.quota_name = p2.quota_name and p1.area_code = p2.area_code and p1.score = p2.score
   where 1=1    
 and lg.code like '35%'   ) p where p.lvl = (select lg.lvl from GSMP_LOC_GRIDS lg where lg.code = 35 )+1 group by p.name,p.code order by score desc ) a
)
```
### 1.1.5 各地市近6个月风险指数趋势
```sql
SELECT
	mon.monthlist years,
	round( nvl( r.score, 0 ), 2 ) score,
	nvl( r.warn, 0 ) warn 
FROM
	(
	SELECT
		TO_CHAR( ADD_MONTHS( add_months( to_date( '202004', 'yyyymm' ),- 6 ), ROWNUM ), 'yyyyMM' ) AS monthlist                                             -- 时间 
	FROM
		DUAL CONNECT BY ROWNUM <= months_between( add_months( to_date( '202004', 'yyyymm' ),- 1 ), add_months( to_date( '202004', 'yyyymm' ),- 6 ) ) + 1    -- 时间 
	) mon
	LEFT JOIN (
	SELECT
		p.years,
		sum( p.score ) score,
		sum( p.warn ) warn 
	FROM
		(
		SELECT
			k.quota_name,
			k.area_code,
			k.years,
			k.warn,
			nvl( k.result_score, 0 ) score 
		FROM
			(
			SELECT
				* 
			FROM
				(
				SELECT
					ROW_NUMBER ( ) OVER ( PARTITION BY r3.quota_id, r3.area_code, r3.years ORDER BY r3.quota_id DESC ) AS ROWR,
					t.quota_name,
					r3.*,
				CASE
						
						WHEN lg.lvl = 1 THEN
						(
						CASE
								
								WHEN w.warn_type = 01 THEN
								( CASE WHEN r3.quota_result_value > w.province_value THEN 1 ELSE 0 END ) 
									WHEN w.warn_type = 02 THEN
									( CASE WHEN r3.quota_result_value > w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
										WHEN w.warn_type = 03 THEN
										( CASE WHEN r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
											WHEN w.warn_type = 04 THEN
											( CASE WHEN r3.quota_result_value < w.province_value THEN 1 ELSE 0 END ) 
												WHEN w.warn_type = 05 THEN
												( CASE WHEN r3.quota_result_value < w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
												END 
												) 
												WHEN lg.lvl = 2 THEN
												(
												CASE
														
														WHEN w.warn_type = 01 THEN
														( CASE WHEN r3.quota_result_value > w.city_value THEN 1 ELSE 0 END ) 
															WHEN w.warn_type = 02 THEN
															( CASE WHEN r3.quota_result_value > w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
																WHEN w.warn_type = 03 THEN
																( CASE WHEN r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
																	WHEN w.warn_type = 04 THEN
																	( CASE WHEN r3.quota_result_value < w.city_value THEN 1 ELSE 0 END ) 
																		WHEN w.warn_type = 05 THEN
																		( CASE WHEN r3.quota_result_value < w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
																		END 
																		) 
																		WHEN lg.lvl = 3 THEN
																		(
																		CASE
																				
																				WHEN w.warn_type = 01 THEN
																				( CASE WHEN r3.quota_result_value > w.county_value THEN 1 ELSE 0 END ) 
																					WHEN w.warn_type = 02 THEN
																					( CASE WHEN r3.quota_result_value > w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																						WHEN w.warn_type = 03 THEN
																						( CASE WHEN r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																							WHEN w.warn_type = 04 THEN
																							( CASE WHEN r3.quota_result_value < w.county_value THEN 1 ELSE 0 END ) 
																								WHEN w.warn_type = 05 THEN
																								( CASE WHEN r3.quota_result_value < w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
																								END 
																								) 
																							END warn 
																			FROM
																				GSMP_QUOTA_SCORE_RESULT r3
																				LEFT JOIN gsmp_quota_info t ON t.id = r3.quota_id
																				LEFT JOIN gsmp_quota_warn_set w ON r3.quota_id = w.quota_id
																				LEFT JOIN gsmp_loc_grids lg ON r3.area_code = lg.code 
																			WHERE
																				1 = 1 
																				AND lg.code = '3501'  -- 福州-平潭 3501 - 3510
																			) N 
																		WHERE
																			N.ROWR = 1 
																		) k 
																	) p 
																GROUP BY
																	p.years 
																) r ON r.years = mon.monthlist 
															ORDER BY
															mon.monthlist,
	warn DESC
```
### 1.1.6 涉稳事件报送情况(含维稳-等级)
```sql
with temp as 
 (select t.*, ui.label report_org_name from (
    select t.id, 
                 '事件库' dbtype, 
                 CASE_CODE code, 
                 case_name name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 CASE_LEVEL assess_level, 
                 case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 CASE_DETAIL DETAIL,REMARK,t.DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID,t.time_created             from gsmp_evt_serious_case t
 union all 
          select t.id, 
                 '隐患库' dbtype, 
                 RISK_CODE code, 
                 RISK_NAME name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 RISK_LEVEL assess_level, 
                 RISK_TYPE case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 RISK_DETAIL DETAIL,REMARK,null DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID,t.time_created            from gsmp_evt_serious_risk t 
           ) t 
    left join usms.usms_institutions ui 
      on ui.id = t.REPORT_ORG_ID 
   where 1 = 1 
  and t.time_created > to_date('20200101', 'YYYYMMDD')                                  --------设置统计当年的一月一号
 ) 
 
 
select lg.code, substr(lg.name,0,2) name,

(select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
and t.dbtype = '事件库'
   and to_char(t.time_created,'YYYYMM')  = '202005') as 本月事件,                       --------设置统计本月的月份
   
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.dbtype = '事件库'
   and  t.time_created <= sysdate )  as 本年事件,
   
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
and t.dbtype = '隐患库'
   and to_char(t.time_created,'YYYYMM')  = '202005') as 本月隐患,                       --------设置统计本月的月份
   
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.dbtype = '隐患库'
   and t.time_created <= sysdate)  as 本年隐患,
   
     
		 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and to_char(t.time_created,'YYYYMM')  = '202005') as 本月小计,                       --------设置统计本月的月份
   
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.time_created <= sysdate)  as 本年小计
  ,
   
   (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level in(1, 01)
   and t.time_created <= sysdate)  as 本年一般危险
   ,
   (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(2, 02)
   and t.time_created <= sysdate)  as 本年较高危险
   ,(select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(3, 03)
   and t.time_created <= sysdate)  as 本年重大危险
   ,(select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(4, 04)
   and t.time_created <= sysdate)  as 本年特别严重危险
   ,
   
   (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level in(1, 01)
   and to_char(t.time_created,'YYYYMM')  = '202005')  as 本月一般危险                    --------设置统计本月的月份
   ,
	 
   (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(2, 02)
   and to_char(t.time_created,'YYYYMM')  = '202005')  as 本月较高危险                    --------设置统计本月的月份
   ,
	 
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(3, 03)
   and to_char(t.time_created,'YYYYMM')  = '202005')  as 本月重大危险                    --------设置统计本月的月份
   ,
	 
	 (select count(1) from temp t where t.belong_area_codes like lg.code||'%' 
   and t.assess_level  in(4, 04)
   and to_char(t.time_created,'YYYYMM')  = '202005')  as 本月特别严重危险                --------设置统计本月的月份
  
	from (select * from gsmp_loc_grids_V) lg 
where 1=1
and (lg.code = 11 or length(lg.code) = 4 )
 order by lg.code asc 

```
### 1.1.7 类型分布
```sql
with temp as ( 
 select t.* from ( 
 select t1.*, 
 row_number() over(partition by t1.id order by t1.time_created desc) rowindex 
 from  (select t.*, ui.label report_org_name from (
    select t.id, 
                 '事件库' dbtype, 
                 CASE_CODE code, 
                 case_name name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 CASE_LEVEL assess_level, 
                 case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 CASE_DETAIL DETAIL,REMARK,t.DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_case t
 union all 
          select t.id, 
                 '隐患库' dbtype, 
                 RISK_CODE code, 
                 RISK_NAME name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 RISK_LEVEL assess_level, 
                 RISK_TYPE case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 RISK_DETAIL DETAIL,REMARK,null DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_risk t 
           ) t 
    left join usms.usms_institutions ui 
      on ui.id = t.REPORT_ORG_ID 
   where 1 = 1 
 and t.OCCUR_DATE  >= '2020-05-01'  and t.OCCUR_DATE  < '2020-06-01'      ) t1           ----------修改时间：本月和本年
 ) t where t.rowindex = 1 
     ) 
select cd.name, nvl(count(t.name), 0) value 
  from gsmp_comm_dictionary cd 
  left join temp t 
    on ',' || t.case_type || ',' like '%,' || cd.code_value ||  
',%' 
 where 1 = 1 
   and cd.key = 'evt_case_type'  
 group by cd.name 
 order by value desc 

```
### 1.1.8 行业分布
```sql
with temp as ( 
 select t.* from ( 
 select t1.*, 
 row_number() over(partition by t1.id order by t1.time_created desc) rowindex 
 from  (select t.*, ui.label report_org_name from (
    select t.id, 
                 '事件库' dbtype, 
                 CASE_CODE code, 
                 case_name name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 CASE_LEVEL assess_level, 
                 case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 CASE_DETAIL DETAIL,REMARK,t.DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_case t
 union all 
          select t.id, 
                 '隐患库' dbtype, 
                 RISK_CODE code, 
                 RISK_NAME name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 RISK_LEVEL assess_level, 
                 RISK_TYPE case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 RISK_DETAIL DETAIL,REMARK,null DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_risk t 
           ) t 
    left join usms.usms_institutions ui 
      on ui.id = t.REPORT_ORG_ID 
   where 1 = 1 
 and t.OCCUR_DATE  >= '2020-05-01'  and t.OCCUR_DATE  < '2020-06-01'      ) t1           ----------修改时间：本月和本年
 ) t where t.rowindex = 1 
     ) 
select cd.name, nvl(count(t.name), 0) value 
  from gsmp_comm_dictionary cd 
  left join temp t 
    on ',' || t.INDUSTRY_FIELD || ',' like '%,' || cd.code_value ||  
',%' 
 where 1 = 1 
   and cd.key = 'industry_sector'  
 group by cd.name 
 order by value desc 

```
### 1.1.9 区县分布
```sql
with temp as ( 
 select t.* from ( 
 select t1.*, 
 row_number() over(partition by t1.id order by t1.time_created desc) rowindex 
 from  (select t.*, ui.label report_org_name from (
    select t.id, 
                 '事件库' dbtype, 
                 CASE_CODE code, 
                 case_name name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 CASE_LEVEL assess_level, 
                 case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 CASE_DETAIL DETAIL,REMARK,t.DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_case t
 union all 
          select t.id, 
                 '隐患库' dbtype, 
                 RISK_CODE code, 
                 RISK_NAME name, 
                 OCCUR_AREA_CODE, 
                 to_char(OCCUR_DATE,'yyyy-MM-dd') OCCUR_DATE, 
                 TIME_CREATED, 
                 JOIN_NUM, 
                 RESPONS_POP, 
                 CONTACT, 
                 GOVER_ORG_ID, 
                 ASSIST_ORG_IDS, 
                 t.create_org_id REPORT_ORG_ID, 
                 BELONG_AREA_CODES, 
                 RISK_LEVEL assess_level, 
                 RISK_TYPE case_type, 
                 INDUSTRY_FIELD, 
                 RESPONS_MEASURE, 
                 DISPOSE_INFO, 
                 RISK_DETAIL DETAIL,REMARK,null DEATH_NUM 
,0 is_pushs,CREATE_ORG_ID            from gsmp_evt_serious_risk t 
           ) t 
    left join usms.usms_institutions ui 
      on ui.id = t.REPORT_ORG_ID 
   where 1 = 1 
and t.OCCUR_DATE  >= '2020-05-01'  and t.OCCUR_DATE  < '2020-06-01'      ) t1           ----------修改时间：本月和本年
 ) t where t.rowindex = 1 
     ) 
select substr(lg.name,0,2) name, nvl(count(t.name), 0) value 
  from (select * from gsmp_loc_grids union select * from gsmp_loc_grids_bj) lg 
  left join temp t 
   on t.OCCUR_AREA_CODE like lg.code||'%' 
 where length(lg.code) = 6 group by substr(lg.name,0,2) 
 order by value desc 

```
### 1.1.10 网络建设
```sql
select substr(lg.name, 0, 2) name,
       t.lvl4 "乡镇街道网格",
       t.lvl5 "社区村居网格",
       t.lvl6 "单元网格",
       t2.dgcmc_count "综治中心",
       t3.dgpi_count "网格员（长）",
       t4.dgmdt_count "群防群治队伍",
       t5.dgcvm_count "视频监控"
  from GSMP_LOC_GRIDS lg
  LEFT JOIN (select substr(lg.code, 0, 4) code,
                    sum(case
                          when lg.lvl = 4 then
                           1
                          else
                           0
                        end) lvl4,
                    sum(case
                          when lg.lvl = 5 then
                           1
                          else
                           0
                        end) lvl5,
                    sum(case
                          when lg.lvl = 6 then
                           1
                          else
                           0
                        end) lvl6
               from GSMP_LOC_GRIDS lg
             
              GROUP BY substr(lg.code, 0, 4)) t
    on t.code = lg.code
  left join (select substr(lg.code, 0, 4) code,
                    sum(case
                          when dgcmc.id is not null then
                           1
                          else
                           0
                        end) dgcmc_count
               from GSMP_LOC_GRIDS lg
               left join DWD_GRID_COM_MANAGE_CENTER dgcmc
                 on dgcmc.grid_num = lg.code
              GROUP BY substr(lg.code, 0, 4)) t2
    on t2.code = lg.code
  left join (select substr(lg.code, 0, 4) code,
                    sum(case
                          when dgpi.id is not null then
                           1
                          else
                           0
                        end) dgpi_count
               from GSMP_LOC_GRIDS lg
               left join DWD_GRID_PERSON_INFO dgpi
                 on substr(dgpi.city_gov_division, 0, 4) = lg.code
              GROUP BY substr(lg.code, 0, 4)) t3
    on t3.code = lg.code

  left join (select substr(lg.code, 0, 4) code,
                    sum(case
                          when dgmdt.id is not null then
                           1
                          else
                           0
                        end) dgmdt_count
               from GSMP_LOC_GRIDS lg
               left join DWD_GRID_MASS_DEFEND_TEAM dgmdt
                 on substr(dgmdt.city_gov_division, 0, 4) = lg.code
              GROUP BY substr(lg.code, 0, 4)) t4
    on t4.code = lg.code
  left join (select substr(lg.code, 0, 4) code,
                    sum(case
                          when dgcvm.id is not null then
                           1
                          else
                           0
                        end) dgcvm_count
               from GSMP_LOC_GRIDS lg
               left join DWD_GRID_COMM_VID_MON dgcvm
                 on dgcvm.grid_num = lg.code
              GROUP BY substr(lg.code, 0, 4)) t5
    on t5.code = lg.code

 where lg.lvl = 2

 order by lg.code

```
### 1.1.11 指数环比
```sql
select substr(lg.name, 0, 2) area_name, t."环比", t."同比"  from GSMP_LOC_GRIDS lg left join (
select 
t.area_code,
-- 环比（本期数 - 上期数）
t."202004" - t."202003" as "环比",  -- 时间需要修改，当月及上月
-- 同比（本期数 - 去年本期数）
t."202004" - t."201904" as "同比"   -- 时间需要修改，当月及去年本月
from (
select * from (
SELECT
  mon.monthlist years,
  round( nvl( r.score, 0 ), 2 ) score,
  r.AREA_CODE
FROM
  (
-- 时间需要修改，当月及上月、去年本月及去年上月
select '201903' as monthlist from dual 
union 
select '202003' as monthlist from dual 
union 
select '201904' as monthlist from dual 
union 
select '202004' as monthlist from dual                                                                  
  ) mon
  LEFT JOIN (
  SELECT
    p.years,
    p.AREA_CODE,
    sum( p.score ) score,
    sum( p.warn ) warn 
  FROM
    (
    SELECT
      k.quota_name,
      k.area_code,
      k.years,
      k.warn,
      nvl( k.result_score, 0 ) score 
    FROM
      (
      SELECT
        * 
      FROM
        (
        SELECT
          ROW_NUMBER ( ) OVER ( PARTITION BY r3.quota_id, r3.area_code, r3.years ORDER BY r3.quota_id DESC ) AS ROWR,
          t.quota_name,
          r3.*,
        CASE
            
            WHEN lg.lvl = 1 THEN
            (
            CASE
                
                WHEN w.warn_type = 01 THEN
                ( CASE WHEN r3.quota_result_value > w.province_value THEN 1 ELSE 0 END ) 
                  WHEN w.warn_type = 02 THEN
                  ( CASE WHEN r3.quota_result_value > w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
                    WHEN w.warn_type = 03 THEN
                    ( CASE WHEN r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
                      WHEN w.warn_type = 04 THEN
                      ( CASE WHEN r3.quota_result_value < w.province_value THEN 1 ELSE 0 END ) 
                        WHEN w.warn_type = 05 THEN
                        ( CASE WHEN r3.quota_result_value < w.province_value OR r3.quota_result_value = w.province_value THEN 1 ELSE 0 END ) 
                        END 
                        ) 
                        WHEN lg.lvl = 2 THEN
                        (
                        CASE
                            
                            WHEN w.warn_type = 01 THEN
                            ( CASE WHEN r3.quota_result_value > w.city_value THEN 1 ELSE 0 END ) 
                              WHEN w.warn_type = 02 THEN
                              ( CASE WHEN r3.quota_result_value > w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
                                WHEN w.warn_type = 03 THEN
                                ( CASE WHEN r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
                                  WHEN w.warn_type = 04 THEN
                                  ( CASE WHEN r3.quota_result_value < w.city_value THEN 1 ELSE 0 END ) 
                                    WHEN w.warn_type = 05 THEN
                                    ( CASE WHEN r3.quota_result_value < w.city_value OR r3.quota_result_value = w.city_value THEN 1 ELSE 0 END ) 
                                    END 
                                    ) 
                                    WHEN lg.lvl = 3 THEN
                                    (
                                    CASE
                                        
                                        WHEN w.warn_type = 01 THEN
                                        ( CASE WHEN r3.quota_result_value > w.county_value THEN 1 ELSE 0 END ) 
                                          WHEN w.warn_type = 02 THEN
                                          ( CASE WHEN r3.quota_result_value > w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
                                            WHEN w.warn_type = 03 THEN
                                            ( CASE WHEN r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
                                              WHEN w.warn_type = 04 THEN
                                              ( CASE WHEN r3.quota_result_value < w.county_value THEN 1 ELSE 0 END ) 
                                                WHEN w.warn_type = 05 THEN
                                                ( CASE WHEN r3.quota_result_value < w.county_value OR r3.quota_result_value = w.county_value THEN 1 ELSE 0 END ) 
                                                END 
                                                ) 
                                              END warn 
                                      FROM
                                        GSMP_QUOTA_SCORE_RESULT r3
                                        LEFT JOIN gsmp_quota_info t ON t.id = r3.quota_id
                                        LEFT JOIN gsmp_quota_warn_set w ON r3.quota_id = w.quota_id
                                        LEFT JOIN gsmp_loc_grids lg ON r3.area_code = lg.code 
                                      WHERE
                                        1 = 1 
                                        AND lg.code in (select code from GSMP_LOC_GRIDS where lvl = 2)   -- 福州-平潭 3501 - 3510
                                      ) N 
                                    WHERE
                                      N.ROWR = 1 
                                    ) k 
                                  ) p 
                                GROUP BY
                                  p.years,
                                p.AREA_CODE 
                                ) r ON r.years = mon.monthlist 
  ) pivot (max(score) for years in (201903, 202003, 201904, 202004))                  -- 需要根据上面的时间进行修改
  ) t ORDER BY t.area_code
	) t on t.area_code = lg.code  where 1=1 and lg.lvl = 2
```
## 1.2 数据备份脚本
- 放在88服务器上任务计划程序中设置了定时任务
- 路径：D:\db_bak
### 1.2.1 event(涉稳事件库)
```shell script
echo 开始备份数据库

if not exist D:\db_bak\files\event md D:\db_bak\files\event

if not exist D:\db_bak\logs\event md D:\db_bak\logs\event

 

set var=%date:~0,4%%date:~5,2%%date:~8,2%

exp event/event#2019@192.168.58.3:1521/gsmp file=D:\db_bak\files\event\event_%var%.dmp log=D:\db_bak\logs\event\event_%var%.log owner=(event)

 

echo 删除过久的备份记录

forfiles /p "D:\db_bak\files\event" /s /m  *.dmp /d -180 /c "cmd /c del @path"

forfiles /p "D:\db_bak\logs\event" /s /m  *.log /d -180 /c "cmd /c del @path"

REM pause
exit
```
### 1.2.2 gsmp
```shell script

echo 开始备份数据库

if not exist D:\db_bak\files\gsmp md D:\db_bak\files\gsmp

if not exist D:\db_bak\logs\gsmp md D:\db_bak\logs\gsmp

 

set var=%date:~0,4%%date:~5,2%%date:~8,2%

exp gsmp/Zo#h817_W@192.168.58.3:1521/gsmp file=D:\db_bak\files\gsmp\gsmp_%var%.dmp log=D:\db_bak\logs\gsmp\gsmp_%var%.log owner=(gsmp)

 

echo 删除过久的备份记录

forfiles /p "D:\db_bak\files\gsmp" /s /m  *.dmp /d -180 /c "cmd /c del @path"

forfiles /p "D:\db_bak\logs\gsmp" /s /m  *.log /d -180 /c "cmd /c del @path"

REM pause
exit
```
### 1.2.3 task
```shell script
echo 开始备份数据库

if not exist D:\db_bak\files\task md D:\db_bak\files\task

if not exist D:\db_bak\logs\task md D:\db_bak\logs\task

 

set var=%date:~0,4%%date:~5,2%%date:~8,2%

exp task/task#2019@192.168.58.3:1521/gsmp file=D:\db_bak\files\task\task_%var%.dmp log=D:\db_bak\logs\task\task_%var%.log owner=(task)

 

echo 删除过久的备份记录

forfiles /p "D:\db_bak\files\task" /s /m  *.dmp /d -180 /c "cmd /c del @path"

forfiles /p "D:\db_bak\logs\task" /s /m  *.log /d -180 /c "cmd /c del @path"

REM pause
exit
```
### 1.2.4 usms
```shell script
echo 开始备份数据库

if not exist D:\db_bak\files\usms md D:\db_bak\files\usms

if not exist D:\db_bak\logs\usms md D:\db_bak\logs\usms

 

set var=%date:~0,4%%date:~5,2%%date:~8,2%

exp usms/usms#2019@192.168.58.3:1521/gsmp file=D:\db_bak\files\usms\usms_%var%.dmp log=D:\db_bak\logs\usms\usms_%var%.log owner=(usms)

 

echo 删除过久的备份记录

forfiles /p "D:\db_bak\files\usms" /s /m  *.dmp /d -180 /c "cmd /c del @path"

forfiles /p "D:\db_bak\logs\usms" /s /m  *.log /d -180 /c "cmd /c del @path"

REM pause
exit
```
### 1.2.5 eval
```shell script

echo 开始备份数据库

if not exist D:\db_bak\files\eval md D:\db_bak\files\eval

if not exist D:\db_bak\logs\eval md D:\db_bak\logs\eval

 

set var=%date:~0,4%%date:~5,2%%date:~8,2%

exp eval/eval#2019@192.168.58.3:1521/gsmp file=D:\db_bak\files\eval\eval_%var%.dmp log=D:\db_bak\logs\eval\eval_%var%.log owner=(eval)

 

echo 删除过久的备份记录

forfiles /p "D:\db_bak\files\eval" /s /m  *.dmp /d -180 /c "cmd /c del @path"

forfiles /p "D:\db_bak\logs\eval" /s /m  *.log /d -180 /c "cmd /c del @path"

REM pause
exit
```
# 2 平网数据同步到省网
- 放在88服务器上，设置了服务，要修改代码，替换jar包，需要停止服务后才能替换成功
- 设置成服务的方式是：https://www.cnblogs.com/FengGeBlog/p/11063735.html
- 路径：D:\dataSync\cmps_pt
## 2.1 代码地址
- https://github.com/lyx990593218/MultiDSDataSyncPro.git
## 2.2 配置说明
- 路径：D:\dataSync\cmps_pt\conifg\application.yml
- 在项目中修改配置文件后，只是打包不能生效，需要把对应配置项放到上述路径下才能生效
```yaml
#开发环境配置
server:
  #服务端口
  port: 8090
  tomcat:
    # tomcat的URI编码
    uri-encoding: UTF-8
    # tomcat最大线程数，默认为200
    max-threads: 200
    # Tomcat启动初始化的线程数，默认值25
    min-spare-threads: 25

#spring 环境配置
spring:
  thymeleaf: 
      prefix: classpath:/templates/
      suffix: .html
      mode: LEGACYHTML5
      encoding: UTF-8
      content-type: text/html
      cache: false
  resources:
    chain:
      strategy:
        content:
          enabled: false
          paths:  /** 
  #数据源
  datasource:
    ds1: #数据源1
      type: com.alibaba.druid.pool.DruidDataSource
      driver-class-name: oracle.jdbc.driver.OracleDriver
      url: jdbc:oracle:thin:@172.26.168.104:1521/gsmp
      username: cmps_pt # 用户名
      password: cmps_pt#2017 # 密码
      max-idle: 10
      max-wait: 1
      min-idle: 5
      initial-size: 5
      validation-query: SELECT 1 FROM DUAL
      test-on-borrow: false
      test-while-idle: true
      time-between-eviction-runs-millis: 18800
    ds2: # 数据源2
      type: com.alibaba.druid.pool.DruidDataSource
      driver-class-name: oracle.jdbc.driver.OracleDriver
      url: jdbc:oracle:thin:@192.168.58.3:1521/wgqzcjk
      username: PINGTAN_LS # 用户名
      password: PINGtan_ls##123 # 密码
      max-idle: 10
      max-wait: 1
      min-idle: 5
      initial-size: 5
      validation-query: SELECT 1 FROM DUAL
      test-on-borrow: false
      test-while-idle: true
      time-between-eviction-runs-millis: 18800
#日志配置
logging:
  path: d:\\dataSync\\cmps_pt\\syncDatalogs     # 如果换路径需要同步修改，否则日志会乱
  level:
    net.evecom.service.impl: DEBUG
    org.springframework: INFO
    org.spring.springboot.dao: DEBUG

## 定时任务开关按钮 open/close
taskSwitch:
  cmps:
    pop: open
    localpop: open
    flowpop: open

    aidsPop: open
    comManageCenter: open
    correctPerson: open
    drugPop: open
    emancipistPop: open
    eventDisLitigantInfo: open
    eventDisputeResolve: open
    gridBasicInfo: open
    gridPersonInfo: open
    gridPopJoin: open
    gridUnitInfo: open
    keyYouth: open
    massDefendOrg: open
    massDefendTeam: open
    mentalPatient: open
  delcmps:
    pop: close
    localpop: close
    flowpop: close

    aidsPop: open
    comManageCenter: open
    correctPerson: open
    drugPop: open
    emancipistPop: open
    eventDisLitigantInfo: open
    eventDisputeResolve: open
    gridBasicInfo: open
    gridPersonInfo: open
    gridPopJoin: open
    gridUnitInfo: open
    keyYouth: open
    massDefendOrg: open
    massDefendTeam: open
    mentalPatient: open
    
## 定时任务表达式配置项
cron:
  cmps:
    pop: 33 32 2 * * ?
    localpop: 33 55 2 * * ?
    flowpop: 33 55 3 * * ?

    aidsPop: 00 5 1 * * ?
    comManageCenter: 00 55 1 * * ?
    correctPerson: 00 55 2 * * ?
    drugPop: 00 7 3 * * ?
    emancipistPop: 00 55 4 * * ?
    eventDisLitigantInfo: 00 55 5 * * ?
    eventDisputeResolve: 00 9 6 * * ?
    gridBasicInfo: 00 00 01 1 * ?
    gridPersonInfo: 00 00 01 1 * ?
    gridPopJoin: 00 00 01 1 * ?
    gridUnitInfo: 00 00 01 1 * ?
    keyYouth: 00 55 7 * * ?
    massDefendOrg: 00 55 8 * * ?
    massDefendTeam: 00 47 8 * * ?
    mentalPatient: 00 23 8 * * ?
    
  delcmps:                         ## 每周一执行删除功能
    pop: 0 10 3 ? * 2
    localpop: 0 10 4 ? * 2
    flowpop: 0 10 5 ? * 2

    aidsPop: 0 10 2 ? * 2
    comManageCenter: 0 10 2 ? * 2
    correctPerson: 0 10 2 ? * 2
    drugPop: 0 10 2 ? * 2
    emancipistPop: 0 10 2 ? * 2
    eventDisLitigantInfo: 0 10 2 ? * 2
    eventDisputeResolve: 0 10 2 ? * 2
    gridBasicInfo: 0 10 2 ? * 2
    gridPersonInfo: 0 10 2 ? * 2
    gridPopJoin: 0 10 2 ? * 2
    gridUnitInfo: 0 50 14 ? * 2
    keyYouth: 0 10 2 ? * 2
    massDefendOrg: 0 10 2 ? * 2
    massDefendTeam: 0 10 2 ? * 2
    mentalPatient: 0 10 2 ? * 2
    
# 查询过去多少天（现设为过去1天的数据）
daysRangeEdge:
  pop: 1
  localpop: 1
  flowpop: 1

  aidsPop: 1
  comManageCenter: 1
  correctPerson: 1
  drugPop: 1
  emancipistPop: 1
  eventDisLitigantInfo: 1
  eventDisputeResolve: 1
  keyYouth: 1
  massDefendOrg: 1
  massDefendTeam: 1
  mentalPatient: 1
  
# 全表新增-开关（有时间为null的查不到）
sysDebug:
  pop: false
  localpop: false
  flowpop: false

  aidsPop: false
  comManageCenter: false
  correctPerson: false
  drugPop: false
  emancipistPop: false
  eventDisLitigantInfo: false
  eventDisputeResolve: false
  keyYouth: false
  massDefendOrg: false
  massDefendTeam: false
  mentalPatient: false
  
#对接的clienId（正式）
#sycBaseUrl: http://newapi.fjsq.fj.cegn.cn
#clientid: 8820178908
#secretkey: tlw8wd1da9eypdrq3m070csp2pmeuqo3
#对接的clienId（测试）
sycBaseUrl: http://api.beta.aishequ.org
#clientid: 7404918535
#secretkey: s01yc5f6qe7dp7bujouv2d85nfj1r57m
clientid: 8820178908
secretkey: tlw8wd1da9eypdrq3m070csp2pmeuqo3
#事件对接登录用户名密码（正式）
#evtusername: nhadmin
#password: d0278017825373a7ea9a94b62d55e429
#事件对接登录用户名密码（测试）
evtusername: sanming
password: 8b8418fa89b5ff58b4503319cbed8827
#系统地址前缀（正式）
#cmpsDomain: http://172.24.3.57:8073/cmps
#cmpsDomain2: http://172.24.3.57:8072/scnh
downloadDomain: http://10.10.10.217:8072/scnh
#系统地址前缀（测试）
cmpsDomain: http://110.85.58.153:7010/cmps
cmpsDomain2: http://110.85.58.153:7020/emnh
#一次性对接多少
onceSyncRows: 100
#错误日志位置
errorDatalogs:  d:\\dataSync\\cmps_pt\\errorDatalogs       # 如果换路径需要同步修改，否则日志会乱
#网格文件地址
gridPath:

```
# 3 CMPSPT项目导入流动人口
## 3.1 流动人口
- 已改造导入接口，完成自动导入，没有实有人口的会新增为实有人口后添加为流动人口
- 最大支持上千条，已经过压测，但要保证网络顺畅，dblink不能断
- 模板直接下载，按照模板导入即可
