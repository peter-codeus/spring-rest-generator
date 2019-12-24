/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codeus.springappgenerator.Entities;

import java.util.ArrayList;
import java.util.List;

import com.codeus.springappgenerator.Util.Util;
import com.codeus.springappgenerator.Util.Util;
/**
 *
 * @author PC
 */
public class DatabaseTable {

    String name, attributeName;
    List<DatabaseColumn> columns;
    String data = "";
    
    public DatabaseTable(String name) {
        this.name = Util.transformCase(name, Case.CAMEL);
        attributeName = Util.transformCase(name, Case.ATTRIBUTE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatabaseColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DatabaseColumn> columns) {
        this.columns = columns;
    }

    public String getEntity() {
        return "";
    }

    public String getDto(String basePackage) {
        
        String importStatement = "import "+basePackage+".Entities."+name+";\n\n";
        String packageDeclaration = "package "+basePackage+".Dtos;\n\n";
        String constructor = "";
        String attributeDeclaration = "";
        String getterSetter = "";
        String setAttributes = "\tpublic "+name+" to"+name+"()\n\t{\n\t\t"+name+" "+attributeName+" = new "+name+"();";
        String getAttributes = "\tpublic "+name+"Dto from"+name+"("+name+" "+attributeName+")\n\t{";
        String dto = "";

        System.out.println("======="+name+"========");
        for (DatabaseColumn c : columns) {            
            //attributeDeclaration
            attributeDeclaration += "\tPrivate " + c.getDataType() + " " + c.getAttributeName()+";\n";
            
            //constructor
            constructor = "\tpublic "+name+"Dto() {}\n";
                    
            //getters
            getterSetter+="\tpublic "+c.getDataType()+" get"+c.getName()+"() { return "+c.getAttributeName()+"; } \n";
            
            //setters
            getterSetter+="\tpublic void set"+c.getName()+"("+c.getDataType()+" "+c.getAttributeName()+") { this."+c.getAttributeName()+" = "+c.getAttributeName()+"; } \n\n";
            
            
            //Getting Atrributes
            getAttributes+="\n\t\t"+c.getAttributeName()+" = "+ attributeName+".get"+c.getName()+"();";

            //Setting Attributes
            setAttributes+="\n\t\t"+attributeName+".set"+c.getName()+"("+c.getAttributeName()+");";
            
        }
        getAttributes+="\n\t\treturn this;\n\t}\n";
        setAttributes+="\n\t\treturn this;\n\t}\n";
        
        dto+=attributeDeclaration+="\n\n";
        dto+=constructor+="\n";
        dto+=getAttributes+"\n";
        dto+=setAttributes+"\n";
        dto+=getterSetter;
        dto=packageDeclaration+importStatement+"public class "+name+"Dto {\n\n"+dto+"}";
        return dto;
    }

    public String getRepository(String basePackage) {
        
        System.out.println("======="+name+"Repository========");
        String packageDeclaration = "package "+basePackage+".Repositories;\n\n";
       
        String importDeclaration = "import "+basePackage+".Entities."+name+";\n";
        importDeclaration+="import org.springframework.data.jpa.repository.JpaRepository;\n\n";
        
        String methodDeclaration = "";
        
        for(DatabaseColumn c: columns)
        {
            if(c.getKey() == DatabaseKey.UNIQUE)
            {
                methodDeclaration+="\tpublic "+name+" findBy"+c.getName()+"("+c.getDataType()+" "+c.getAttributeName()+");\n";
            }
        }
        String repo = "public interface "+name+"Repository extends JpaRepository<"+name+", Integer>\n{\n"+methodDeclaration+"} \n\n";
        repo = packageDeclaration + importDeclaration + repo;
        return repo;
    }

    public String getController(String basePackage) {

        System.out.println("======="+name+"Controller========");

        String packageDeclaration = "package "+basePackage+".Controllers;\n\n";
        
        String importStatement = "import "+basePackage+".Entities."+name+";\n";
        importStatement+="import "+basePackage+".Repositories."+name+"Repository;\n";
        importStatement+="import "+basePackage+".Dtos."+name+"Dto;\n";
        importStatement+="import org.springframework.beans.factory.annotation.Autowired;\n" +
                         "import org.springframework.web.bind.annotation.CrossOrigin;\n" +
                         "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                         "import org.springframework.web.bind.annotation.RestController;\n"+
                         "import org.springframework.web.bind.annotation.DeleteMapping;\n" +
                         "import org.springframework.web.bind.annotation.PutMapping;\n" +
                         "import org.springframework.web.bind.annotation.PathVariable;\n" +
                         "import org.springframework.web.bind.annotation.PostMapping;\n" +
                         "import org.springframework.web.bind.annotation.RequestBody;\n"+ 
                         "import java.util.List;\n" +
                         "import java.util.Optional;\n" +
                         "import org.springframework.beans.factory.annotation.Autowired;\n" +
                         "import org.springframework.http.HttpStatus;\n"+ 
                         "import org.springframework.web.bind.annotation.GetMapping;\n" +
                         "import java.util.ArrayList;\n\n";
        
        String repositories = "\t@Autowired\n\t"+name+"Repository "+attributeName+"Repository;\n";
        
        String findByIdMethod = "\n\t@GetMapping(\"/{id}\")\n" +
                          "\tpublic ResponseEntity<"+name+"Dto> finById(@PathVariable() Integer id)\n" +
                          "\t{\n"+
                                 "\t\tOptional<"+name+"> "+attributeName+" = "+attributeName+"Repository.findById(id);\n"+
                                "\t\tif("+attributeName+".isPresent())\n" +
                                 "\t\t{\n"+
                                 "\t\t\treturn new ResponseEntity(new "+name+"Dto().from"+name+"("+attributeName+"),HttpStatus.OK);\n"+
                                 "\t\t}\n"+
                                 "\t\telse\n"+
                                 "\t\t\treturn new ResponseEntity(HttpStatus.NOT_FOUND);"+
                        "\n\t}\n\n";
        
        String findAllMethod = "\t@GetMapping\n" +
                           "\tpublic ResponseEntity<List<"+name+"Dto>> findAll()\n" +
                           "\t{\n"+
                                "\t\tList<"+name+"Dto> "+attributeName+"s = new ArrayList<>();\n" +
                                "\t\t"+attributeName+"Repository.findAll().forEach(elm -> {\n" +
                                "\t\t\t"+attributeName+"s.add(new "+name+"Dto().from"+name+"(elm));\n" +
                                "\t\t});\n" +
                                "\t\treturn new ResponseEntity("+attributeName+"s, HttpStatus.OK);"+
                           "\n\t}\n\n";
        
        String saveMethod = "\t@PostMapping\n" +
                        "\tpublic ResponseEntity<"+name+"Dto> save(@RequestBody UserDto userDto)\n" +
                        "\t{\n";
        
        saveMethod+="\t\tList<String> errorList = new ArrayList<>();\n\n";
        
        for(DatabaseColumn c: columns)
        {
            if(!c.getNullable() && c.getKey() != DatabaseKey.PRIMARY)
                saveMethod+="\t\tif ("+attributeName+"Dto.get"+c.getName()+"() == null)\n\t\t\t errorList.add(new String(\""+c.getName()+"_REQUIRED\"));\n";
        }
        
        saveMethod+="\n";
        saveMethod+="\t\tif (errorList.size()>0)\n\t\t\treturn new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);\n\n";
        saveMethod+="\t\terrorList.clear();\n\n";

        for(DatabaseColumn c: columns)
        {
            if(c.getKey() != DatabaseKey.PRIMARY && c.getKey() == DatabaseKey.UNIQUE)
                saveMethod+="\t\tif ("+attributeName+"Repository.findBy"+c.getName()+"("+attributeName+"Dto.get"+c.getName()+"()) != null)\n\t\t\t errorList.add(new String(\""+c.getName()+"_EXIST\"));\n";
        }
        
        saveMethod+="\n";
        saveMethod+="\t\tif (errorList.size()>0)\n\t\t\treturn new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);\n\n";
        saveMethod+="\t\terrorList.clear();\n\n";
        saveMethod+="\n\t}\n\n";
        
        String updateMethod = "\t@PutMapping\n" +
                        "\tpublic ResponseEntity<"+name+"Dto> update(@RequestBody UserDto userDto)\n" +
                        "\t{"
                        + "\n\t}\n\n";
        
        String deleteMethod = "\t@DeleteMapping(\"/{id}\")\n" +
                          "\tpublic ResponseEntity<HttpStatus> delete(@PathVariable() Integer id)\n" +
                          "\t{\n"+
                                "\t\tOptional<"+name+"> "+attributeName+" = "+attributeName+"Repository.findById(id);\n"+
                                "\t\tif("+attributeName+".isPresent())\n" +
                                 "\t\t{\n"+
                                 "\t\t\t"+attributeName+"Repository.delete("+attributeName+".get());\n" +
                                 "\t\t\treturn new ResponseEntity(HttpStatus.OK);\n"+
                                 "\t\t}\n"+
                                 "\t\telse\n"+
                                 "\t\t\treturn new ResponseEntity(HttpStatus.NOT_FOUND);"+
                        "\n\t}";
        
        String controller = "@RestController\n" +
                            "@RequestMapping(\""+attributeName+"\")\n" +
                            "@CrossOrigin(origins = \"*\")\n" +
                            "public class "+name+"Controller {\n" +
                            "    \n" +
                            "    "+repositories +
                            findByIdMethod +
                            findAllMethod +
                            saveMethod +
                            updateMethod +
                            deleteMethod +
                            "    \n" +
                            "}";
        
        controller = packageDeclaration + importStatement + controller;
        
        return controller;
    }

    @Override
    public String toString() {
        this.columns.forEach(c -> {
            data += c.toString() + "\n";
        });
        return "==========" + name + "==========\n" + data;
    }
}
