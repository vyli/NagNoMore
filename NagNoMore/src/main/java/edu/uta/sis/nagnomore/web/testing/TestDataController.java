package edu.uta.sis.nagnomore.web.testing;

import edu.uta.sis.nagnomore.domain.data.*;
import edu.uta.sis.nagnomore.domain.service.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by mare on 11.7.2016.
 */

@Controller
public class TestDataController {


    @Autowired
    CategoryService cs;

    @Autowired
    FamilyService familyService;

    @Autowired
    UserService us;

    @Autowired
    TaskService ts;

    @Autowired
    TaskSkeletonService tss;

    @RequestMapping(value="/test/riinatest")
    public String testMe(){
        return "/jsp/testi/testidata";
    }


    @RequestMapping(value="/react/nagnomore")
    public String pleaseDeliver(){
        return "/jsp/index";
    }

    @RequestMapping(value="/test/getcategories")
    public @ResponseBody List<Category> getCategoryDataViaAjax(){

        List<Category> list = cs.getCategories();

        return list;
    }


    @RequestMapping(value="/test/getfamilies")
    public @ResponseBody List<WwwFamily> getFamilyDataViaAjax(){

        List<WwwFamily> list = familyService.listAllFamilies();

        return list;
    }

    @RequestMapping(value="/test/getfamilymembers")
    public @ResponseBody List<WwwUser> getFamilyMemberDataViaAjax(){

        List<WwwUser> list = us.getUsers();

        return list;
    }

    @RequestMapping(value="/test/gettasks")
    public @ResponseBody List<Task> getTaskDataViaAjax(){

        List<Task> list = ts.findAll();

        return list;
    }

    @RequestMapping(value="/test/puttask", method = RequestMethod.POST)
    public @ResponseBody List<Task> putTaskAndDeliver(@RequestBody TaskSkeleton taskSkeleton){


        System.out.println(taskSkeleton.toString());

        Task task = tss.convertToTask(taskSkeleton);

        ts.addTask(task);


        List<Task> list = ts.findAll();

        return list;

    }

    @RequestMapping(value="/test/putdatatestajax")
    public String postDataViaAjax(@RequestBody List<Category> list){

        Iterator<Category> listIterator = list.iterator();
        while(listIterator.hasNext()){
            System.out.println(listIterator.next().toString());
        }

        return "/jsp/testi/testidata";
    }

    @RequestMapping(value="/test/createdata")
    public String createTestData(){

        deleteAllData();

        //Tarvitaan muutama kategoria. Kopioidaan suoraan yltä Eeron koodis.
        Category ce1 = new Category();
        ce1.setTitle("Harrastukset");
        ce1.setDescription("Kategoria harrastusluonteiselle toiminnalle.");
        cs.create(ce1);

        Category ce2 = new Category();
        ce2.setTitle("Lääkkeet");
        ce2.setDescription("Kategoria muistutuksille lääkkeenottoajoista.");
        cs.create(ce2);

        Category ce3 = new Category();
        ce3.setTitle("Kotityöt");
        ce3.setDescription("Kategoria kotitöille.");
        cs.create(ce3);

        //Tarvitaan yksi perhe. Kopioidaan suoraan Hannan koodia (TestiaHannan-kontrollerista)
        WwwFamily wwwFamily = new WwwFamily();
        wwwFamily.setFamilyName("Rujot");
        familyService.addFamily(wwwFamily);

        WwwFamily familyFromDb = familyService.findFamilyByName("Rujot");


        // Tarvitaan kaksi aikuista ja kolme lasta perheeseen. Kopioidaan suoraan Viljamin koodia.

        //  public WwwUser(Long id, String username, String password, String email, String fullName, String phoneNumber, String role, Boolean enabled)
        WwwUser u1 = new WwwUser(null, "Pekka", "salasana", "pekka@huu.haa", "Pekka Rujo",  "123456789", "ROLE_CHILD", true);
        u1.setFamily(familyFromDb);
        us.create(u1);

        WwwUser u2 = new WwwUser(null, "Ville", "salasana", "ville@huu.haa", "Ville Rujo",  "123456789", "ROLE_PARENT", true);
        u2.setFamily(familyFromDb);
        us.create(u2);

        WwwUser u3 = new WwwUser(null, "Maija", "salasana", "maija@huu.haa", "Maija Rujo",  "123456789", "ROLE_PARENT", true);
        u3.setFamily(familyFromDb);
        us.create(u3);

        WwwUser u4 = new WwwUser(null, "Joona", "salasana", "joona@huu.haa", "Joona Rujo",  "123456789", "ROLE_CHILD", true);
        u4.setFamily(familyFromDb);
        us.create(u4);

        WwwUser u5 = new WwwUser(null, "Mette", "salasana", "mette@huu.haa", "Mette Rujo",  "123456789", "ROLE_CHILD", true);
        u5.setFamily(familyFromDb);
        us.create(u5);



        //Nyt on olemassa kategoriat, perhe ja sen jäsenet. Luodaan useita taskeja erilaisilla kombinaatioilla.
        //Haetaan kaikki tärkeä valmiiksi kannasta. Perheenjäseniä on 5 ja kategorioita 3.

        List<WwwUser> familyMembers = us.getUsersByFamily(familyFromDb);
        List<Category> categories = cs.getCategories();



        Category harrastukset = categories.get(0);
        Category laakkeet = categories.get(1);
        Category kotityot = categories.get(2);


        //Ensin 5 harrastustaskia

        String[] harrastusTitlet = new String[] { "Osta uusi koripallo", "Osta hiekkahousut", "Maksa lisenssi", "Varaa liput", "Selvitä turnaus"};
        String[] harrastusDescit = new String[] { "Koon 4 koripallo. Stadiumissa ei ollut.", "Ennen seuraavaa turnausta", "Heinäkuun aikana", "Tanssiesitykset myydään loppuun", "Majoitus ja kuljetus epäselvää"};
        for(int i = 0; i < 5; i++){
            DateTime now = DateTime.now();
            this.createTestTask(harrastusTitlet[i], harrastusDescit[i], now, createDue(now), createPriority(), createBoolean(), createBoolean(), harrastukset, selectFamilyMember(familyMembers), selectFamilyMember(familyMembers), familyFromDb, selectStatus() );
        }

        // Sitten 5 lääkkeisiin liittyvää
        String[] laakeTitlet = new String[] { "Antibiootit", "Antihistamiini", "Vakuutus", "Sivuvaikutukset", "Peruskorvattavuus"};
        String[] laakeDescit = new String[] { "Kahdeksan tunnin välein", "Aamulla, tarv. illalla", "Hae korvausta kesän lääkkeistä", "Raportoi sivuvaikutukset", "Tilaa B-lausunto peruskorvattavuutta varten"};

        for(int i = 0; i < 5; i++){
            DateTime now = DateTime.now();
            this.createTestTask(laakeTitlet[i], laakeDescit[i], now, createDue(now), createPriority(), createBoolean(), createBoolean(), harrastukset, selectFamilyMember(familyMembers), selectFamilyMember(familyMembers), familyFromDb, selectStatus() );
        }



        // Lopuksi 10 kotitöihin liittyvää
        String[] kotiTitlet = new String[] { "Imurointi", "Ikkunat", "Tiivistys", "Matot", "Vaatehuone", "Lakanat", "Kirjahylly", "Eteinen", "Jääkaappi", "Liesituulettimen lamppu"};
        String[] kotiDescit = new String[] { "Imuroi eteinen ja keittiö", "Pese ikkunat", "Tiivistä ikkunat", "Pese matot", "Järjestä vaatehuoneesta pienet kirpparille", "Vaihda lakanat", "Aakkosta kirjat", "Asenna uusi avainnaulakko", "Jääkaapista vanhat maustekastikkeet plus pesu", "Liesituulettimen lamppu, saa Clas Ohlsonilta, malli 4438910"};

        for(int i = 0; i < 10 ; i++){
            DateTime now = DateTime.now();
            this.createTestTask(kotiTitlet[i], kotiDescit[i], now, createDue(now), createPriority(), createBoolean(), createBoolean(), harrastukset, selectFamilyMember(familyMembers), selectFamilyMember(familyMembers), familyFromDb, selectStatus() );
        }



        return "/jsp/testi/valmis";
    }


    @RequestMapping(value="/test/emptydb")
    public String deleteTestData(){

        deleteAllData();

        return "/jsp/testi/tyhja";
    }

    private void deleteAllData(){
        //Kannassa on dataa tauluissa userentity, task, family, categories
        //Poistojärjestys task -> categories -> userentity -> family


        List<Task> taskList = ts.findAll();

        Iterator<Task> taskListIterator = taskList.iterator();
        while(taskListIterator.hasNext()){
            ts.remove(taskListIterator.next());
        }

        List<Category> categoryList = cs.getCategories();

        Iterator<Category> catListIterator = categoryList.iterator();
        while(catListIterator.hasNext()){
            cs.remove(catListIterator.next());
        }

        List<WwwUser> userList = us.getUsers();

        Iterator<WwwUser> userListIterator = userList.iterator();
        while(userListIterator.hasNext()){
            us.remove(userListIterator.next().getId());
        }

        List<WwwFamily> familyList = familyService.listAllFamilies();

        Iterator<WwwFamily> familyListIterator = familyList.iterator();
        while(familyListIterator.hasNext()){
            familyService.removeFamily(familyListIterator.next().getId());
        }

    }

    private void createTestTask(String title, String description,
                                DateTime created, DateTime due,
                                int priority, Boolean privacy,
                                Boolean alarm, Category category,
                                WwwUser creator, WwwUser assignee,
                                WwwFamily family, Task.Status status
    ) {

        Task t = new Task();

        t.setTitle(title);
        t.setDescription(description);
        t.setCreated(created);
        t.setDue(due);
        t.setPriority(priority);
        t.setPrivacy(privacy);
        t.setAlarm(alarm);
        t.setCategory(category);
        t.setCreator(creator);
        t.setAssignee(assignee);
        t.setStatus(status);
        t.setFamily(family);

        ts.addTask(t);

     }

    private DateTime createDue(DateTime created){
        Random r = new Random();
        //Random int between [1-14]
        int i = r.nextInt(14) + 1;
        return created.plusDays(i);
    }

    private int createPriority(){
        Random r = new Random();
        //Random int between [0-3]
        int i = r.nextInt(4);
        return i;
    }

    private boolean createBoolean(){
        Random r = new Random();
        boolean answer = true;
        int i = r.nextInt(2);
        if(i == 0){
            answer = false;
        }
        else if(i == 1){
            answer = true;
        }
        return answer;
    }

    private WwwUser selectFamilyMember(List<WwwUser> familyMembers){
        Random r = new Random();
        int max = familyMembers.size();
        //index between [0, familyMembers.size()[
        int i = r.nextInt(max);
        return familyMembers.get(i);

    }

    private Task.Status selectStatus(){
        Task.Status[] statuses = new Task.Status[]{Task.Status.NEEDS_ACTION, Task.Status.IN_PROGRESS, Task.Status.COMPLETED};
        Random r = new Random();
        int max = statuses.length ;
        int i = r.nextInt(max);
        return statuses[i];

    }

}
