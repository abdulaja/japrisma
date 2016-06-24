package com.asismedia.japrisma.model;

public class Tourism {

    private long id_tourism;
    private long id_category;
    private long id_area;
    private long id_subarea;
    private String category;
    private String name;
    private String subarea;
    private String area;
    private String indescript;
    private String ininfo;
    private String endescript;
    private String eninfo;
    private String image;
    private String time;


    public Tourism() {

    }


    /**
     * @return the id_tourism
     */
    public long getId_tourism() {
        return id_tourism;
    }

    /**
     * @param id_tourism
     */
    public void setId_tourism(long id_tourism) {
        this.id_tourism = id_tourism;
    }


    /**
     * @return category
     */
    public long getId_category() {
        return id_category;
    }

    /**
     * @param id_category
     */
    public void setId_category(long id_category) {
        this.id_category = id_category;
    }


    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return subarea
     */
    public long getId_subarea() {
        return id_subarea;
    }

    /**
     * @param id_subarea
     */
    public void setId_subarea(long id_subarea) {
        this.id_subarea = id_subarea;
    }


    /**
     * @return subarea
     */
    public String getSubarea() {
        return subarea;
    }

    /**
     * @param subarea
     */
    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }


    /**
     * @return area
     */
    public long getId_area() {
        return id_area;
    }

    /**
     * @param id_area
     */
    public void setId_area(long id_area) {
        this.id_area = id_area;
    }


    /**
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }


    public String getIndescript() {
        return indescript;
    }

    public void setIndescript(String indescript) {
        this.indescript = indescript;
    }


    public String getIninfo() {
        return ininfo;
    }

    public void setIninfo(String ininfo) {
        this.ininfo = ininfo;
    }


    public  String getEndescript() {
        return endescript;
    }

    public void setEndescript(String endescript) {
        this.endescript = endescript;
    }


    public String getEninfo() {
        return eninfo;
    }

    public  void setEninfo(String eninfo) {
        this.eninfo = eninfo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
