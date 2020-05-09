package com.org.androidtools.designmodel.builder;

public class BuilderDes {
    private String name;
    private int age;
    private int ID;

    private BuilderDes(Builder builder){
        this.ID = builder.ID;
        this.age = builder.age;
        this.name = builder.name;
    }

    public static class Builder{
        private int ID;
        private int age;
        private String name;
        public Builder setID(int ID){
            this.ID = ID;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setAge(int age){
            this.age = age;
            return this;
        }
        public BuilderDes build(){
            return new BuilderDes(this);
        }
    }
}
