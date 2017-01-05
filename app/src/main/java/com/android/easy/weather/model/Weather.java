package com.android.easy.weather.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 * 天气的实体类
 */

public class Weather {

    private List<HeWeather> HeWeather;

    public List<HeWeather> getHeWeather() {
        return HeWeather;
    }

    public void setHeWeather(List<HeWeather> HeWeather) {
        this.HeWeather = HeWeather;
    }

    public static class HeWeather {
        /**
         * aqi : {"city":{"aqi":"132","co":"1","no2":"33","o3":"94","pm10":"106","pm25":"100","qlty":"轻度污染","so2":"26"}}
         * basic : {"city":"苏州","cnty":"中国","id":"CN101190401","lat":"31.309000","lon":"120.612000","update":{"loc":"2016-12-29 14:52","utc":"2016-12-29 06:52"}}
         * daily_forecast : [{"astro":{"mr":"06:31","ms":"17:15","sr":"06:55","ss":"17:03"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-12-29","hum":"66","pcpn":"0.0","pop":"0","pres":"1035","tmp":{"max":"7","min":"2"},"uv":"4","vis":"10","wind":{"deg":"358","dir":"北风","sc":"3-4","spd":"11"}},{"astro":{"mr":"07:20","ms":"18:06","sr":"06:55","ss":"17:04"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2016-12-30","hum":"76","pcpn":"0.0","pop":"0","pres":"1031","tmp":{"max":"11","min":"3"},"uv":"3","vis":"10","wind":{"deg":"143","dir":"东南风","sc":"微风","spd":"6"}},{"astro":{"mr":"08:06","ms":"19:01","sr":"06:56","ss":"17:05"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-12-31","hum":"83","pcpn":"0.0","pop":"0","pres":"1030","tmp":{"max":"14","min":"5"},"uv":"4","vis":"10","wind":{"deg":"183","dir":"东风","sc":"微风","spd":"6"}}]
         * hourly_forecast : [{"cond":{"code":"101","txt":"多云"},"date":"2016-12-29 16:00","hum":"58","pop":"0","pres":"1035","tmp":"7","wind":{"deg":"130","dir":"无持续风向","sc":"微风","spd":"12"}},{"cond":{"code":"101","txt":"多云"},"date":"2016-12-29 19:00","hum":"69","pop":"0","pres":"1035","tmp":"5","wind":{"deg":"36","dir":"无持续风向","sc":"微风","spd":"10"}},{"cond":{"code":"100","txt":"晴"},"date":"2016-12-29 22:00","hum":"73","pop":"0","pres":"1035","tmp":"4","wind":{"deg":"46","dir":"无持续风向","sc":"微风","spd":"9"}}]
         * now : {"cond":{"code":"101","txt":"多云"},"fl":"3","hum":"46","pcpn":"0","pres":"1037","tmp":"6","vis":"10","wind":{"deg":"352","dir":"东北风","sc":"4-5","spd":"21"}}
         * status : ok
         * suggestion : {"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"较舒适","txt":"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"易发","txt":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},"sport":{"brf":"较不宜","txt":"天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。"},"trav":{"brf":"一般","txt":"天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。"},"uv":{"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}}
         */

        private Aqi aqi;
        private Basic basic;
        private Now now;
        private String status;
        private Suggestion suggestion;
        private List<DailyForecast> daily_forecast;
        private List<HourlyForecast> hourly_forecast;

        public Aqi getAqi() {
            return aqi;
        }

        public void setAqi(Aqi aqi) {
            this.aqi = aqi;
        }

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Suggestion getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggestion suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecast> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecast> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyForecast> getHourly_forecast() {
            return hourly_forecast;
        }

        public void setHourly_forecast(List<HourlyForecast> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }

        public static class Aqi {
            /**
             * city : {"aqi":"132","co":"1","no2":"33","o3":"94","pm10":"106","pm25":"100","qlty":"轻度污染","so2":"26"}
             */

            private City city;

            public City getCity() {
                return city;
            }

            public void setCity(City city) {
                this.city = city;
            }

            public static class City {
                /**
                 * aqi : 132
                 * co : 1
                 * no2 : 33
                 * o3 : 94
                 * pm10 : 106
                 * pm25 : 100
                 * qlty : 轻度污染
                 * so2 : 26
                 */

                private String aqi;
                private String co;
                private String no2;
                private String o3;
                private String pm10;
                private String pm25;
                private String qlty;
                private String so2;

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }
            }
        }

        public static class Basic {
            /**
             * city : 苏州
             * cnty : 中国
             * id : CN101190401
             * lat : 31.309000
             * lon : 120.612000
             * update : {"loc":"2016-12-29 14:52","utc":"2016-12-29 06:52"}
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private Update update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public Update getUpdate() {
                return update;
            }

            public void setUpdate(Update update) {
                this.update = update;
            }

            public static class Update {
                /**
                 * loc : 2016-12-29 14:52
                 * utc : 2016-12-29 06:52
                 */

                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class Now {
            /**
             * cond : {"code":"101","txt":"多云"}
             * fl : 3
             * hum : 46
             * pcpn : 0
             * pres : 1037
             * tmp : 6
             * vis : 10
             * wind : {"deg":"352","dir":"东北风","sc":"4-5","spd":"21"}
             */

            private Cond cond;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private Wind wind;

            public Cond getCond() {
                return cond;
            }

            public void setCond(Cond cond) {
                this.cond = cond;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public static class Cond {
                /**
                 * code : 101
                 * txt : 多云
                 */

                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Wind {
                /**
                 * deg : 352
                 * dir : 东北风
                 * sc : 4-5
                 * spd : 21
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class Suggestion {
            /**
             * air : {"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}
             * comf : {"brf":"较舒适","txt":"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。"}
             * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
             * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
             * flu : {"brf":"易发","txt":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"}
             * sport : {"brf":"较不宜","txt":"天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。"}
             * trav : {"brf":"一般","txt":"天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。"}
             * uv : {"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}
             */

            private Air air;
            private Comf comf;      //  舒适度指数
            private Cw cw;          //  洗车指数
            private Drsg drsg;      //  穿衣指数
            private Flu flu;        //  感冒指数
            private Sport sport;    //  运动指数
            private Trav trav;      //  旅游指数
            private UvBean uv;      //  紫外线指数

            public Air getAir() {
                return air;
            }

            public void setAir(Air air) {
                this.air = air;
            }

            public Comf getComf() {
                return comf;
            }

            public void setComf(Comf comf) {
                this.comf = comf;
            }

            public Cw getCw() {
                return cw;
            }

            public void setCw(Cw cw) {
                this.cw = cw;
            }

            public Drsg getDrsg() {
                return drsg;
            }

            public void setDrsg(Drsg drsg) {
                this.drsg = drsg;
            }

            public Flu getFlu() {
                return flu;
            }

            public void setFlu(Flu flu) {
                this.flu = flu;
            }

            public Sport getSport() {
                return sport;
            }

            public void setSport(Sport sport) {
                this.sport = sport;
            }

            public Trav getTrav() {
                return trav;
            }

            public void setTrav(Trav trav) {
                this.trav = trav;
            }

            public UvBean getUv() {
                return uv;
            }

            public void setUv(UvBean uv) {
                this.uv = uv;
            }

            public static class Air {
                /**
                 * brf : 良
                 * txt : 气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Comf {
                /**
                 * brf : 较舒适
                 * txt : 白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Cw {
                /**
                 * brf : 较适宜
                 * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Drsg {
                /**
                 * brf : 较冷
                 * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Flu {
                /**
                 * brf : 易发
                 * txt : 昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Sport {
                /**
                 * brf : 较不宜
                 * txt : 天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class Trav {
                /**
                 * brf : 一般
                 * txt : 天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class UvBean {
                /**
                 * brf : 中等
                 * txt : 属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。
                 */

                private String brf;
                private String txt;

                public String getBrf() {
                    return brf;
                }

                public void setBrf(String brf) {
                    this.brf = brf;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
        }

        public static class DailyForecast {
            /**
             * astro : {"mr":"06:31","ms":"17:15","sr":"06:55","ss":"17:03"}
             * cond : {"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"}
             * date : 2016-12-29
             * hum : 66
             * pcpn : 0.0
             * pop : 0
             * pres : 1035
             * tmp : {"max":"7","min":"2"}
             * uv : 4
             * vis : 10
             * wind : {"deg":"358","dir":"北风","sc":"3-4","spd":"11"}
             */

            private Astro astro;
            private CondX cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            private Tmp tmp;
            private String uv;
            private String vis;
            private WindX wind;

            public Astro getAstro() {
                return astro;
            }

            public void setAstro(Astro astro) {
                this.astro = astro;
            }

            public CondX getCond() {
                return cond;
            }

            public void setCond(CondX cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public Tmp getTmp() {
                return tmp;
            }

            public void setTmp(Tmp tmp) {
                this.tmp = tmp;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindX getWind() {
                return wind;
            }

            public void setWind(WindX wind) {
                this.wind = wind;
            }

            public static class Astro {
                /**
                 * mr : 06:31
                 * ms : 17:15
                 * sr : 06:55
                 * ss : 17:03
                 */

                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondX {
                /**
                 * code_d : 100
                 * code_n : 100
                 * txt_d : 晴
                 * txt_n : 晴
                 */

                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class Tmp {
                /**
                 * max : 7
                 * min : 2
                 */

                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindX {
                /**
                 * deg : 358
                 * dir : 北风
                 * sc : 3-4
                 * spd : 11
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }

        public static class HourlyForecast {
            /**
             * cond : {"code":"101","txt":"多云"}
             * date : 2016-12-29 16:00
             * hum : 58
             * pop : 0
             * pres : 1035
             * tmp : 7
             * wind : {"deg":"130","dir":"无持续风向","sc":"微风","spd":"12"}
             */

            private CondXX cond;
            private String date;
            private String hum;
            private String pop;
            private String pres;
            private String tmp;
            private WindXX wind;

            public CondXX getCond() {
                return cond;
            }

            public void setCond(CondXX cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public WindXX getWind() {
                return wind;
            }

            public void setWind(WindXX wind) {
                this.wind = wind;
            }

            public static class CondXX {
                /**
                 * code : 101
                 * txt : 多云
                 */

                private String code;
                private String txt;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }

            public static class WindXX {
                /**
                 * deg : 130
                 * dir : 无持续风向
                 * sc : 微风
                 * spd : 12
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}
