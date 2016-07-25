<%--
  Created by IntelliJ IDEA.
  User: mare
  Date: 11.7.2016
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Testidata</title>

    <link rel="stylesheet" href="<c:url value='/resources/jquery-ui/jquery-ui.css' />"  type="text/css" >

    <style type="text/css">
    </style>

    <script type="text/javascript" src="<c:url value='/resources/js/jquery-2.1.4.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/jquery-ui/jquery-ui.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/datetimepicker.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/lodash.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/moment.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/fullcalendar.js' />" ></script>

    <script type="text/javascript">

    jQuery(document).ready(function(){

        console.log("Initialize app");

        var listdata = [
            { id: 1,  title: "Harrastukset",  description: "Kategoria harrastusluonteiselle toiminnalle."},
            { id: 2,  title: "Lääkkeet",  description: "Kategoria muistutuksille lääkkeenottoajoista."},
            { id: 3,  title: "Kotityöt",  description: "Kategoria kotitöille."},
            { id: 4,  title: "Työ",  description: "Kategoria työasioille."},
            { id: 5,  title: "Koulu",  description: "Kategoria kouluun liittyville asioille."},
            { id: 6,  title: "Talo",  description: "Kategoria taloon liittyville asioille."}
        ];

        var taskFull = {
            "id": undefined,
            "title": "Leivo piiras",
            "description": "Ainakin kymmenelle, mieluusti mustikka, ei maitoa",
            "created": moment.now(),
            "due": moment.now(),
            "priority": 3,
            "privacy": false,
            "alarm": undefined,
            "category": {"id":15,"title":"Kotityöt","description":"Kategoria kotitöille."},
            "family": { id: 5, familyName: "Rujot" },
            "creator": {"id":17,"username":"Ville","password":"salasana","enabled":true,"email":"ville@huu.haa","phoneNumber":"123456789","fullName":"Ville Rujo","family":null,"created":null,"credentialsNonExpired":true,"accountNonExpired":true,"accountNonLocked":true,"role":"ROLE_PARENT","authorities":[{"authority":"ROLE_PARENT"}]},
            "assignee": {"id":17,"username":"Ville","password":"salasana","enabled":true,"email":"ville@huu.haa","phoneNumber":"123456789","fullName":"Ville Rujo","family":null,"created":null,"credentialsNonExpired":true,"accountNonExpired":true,"accountNonLocked":true,"role":"ROLE_PARENT","authorities":[{"authority":"ROLE_PARENT"}]},
            "location": undefined,
            "reminder": undefined,
            "status": "NEEDS_ACTION"

        }

        var task = {
            "id": undefined,
            "title": "Leivo piiras",
            "description": "Ainakin kymmenelle, mieluusti mustikka, ei maitoa",
            "created": moment.now(),
            "due": moment.now(),
            "priority": 3,
            "privacy": false,
            "alarm": false,
            "categoryId": 15,
            "familyId": 5,
            "creatorId": 17,
            "assigneeId": 18,
            "location": undefined,
            "reminder": undefined,
            "status": "NEEDS_ACTION"

        }

        console.log(task);

        $("#getcategorydataform").submit(function(event){

            event.preventDefault();
            getDataViaAjax();
        })

        $("#putcategorydataform").submit(function(event){

            event.preventDefault();
            putDataViaAjax(listdata);
        })

        $("#getalldataform").submit(function(event){

            event.preventDefault();
            getAllDataViaAjax();

        })

        $("#puttaskform").submit(function(event){

            event.preventDefault();
            putTask(task);

        })





    });
    function getAllDataViaAjax(){
        console.log("Got here");

        $.ajax({
            url:"/test/getcategories",
            dataType: 'json',
            contentType: "application/json",
            cache: false,
            success: function(data) {
                console.log(JSON.stringify(data));
            },
            error: function(e) {
                console.log("Error: ", e);
            }
        });



        $.ajax({
            url:"/react/tasks",
            dataType: 'json',
            contentType: "application/json",
            cache: false,
            success: function(data) {
                console.log(JSON.stringify(data));
            },
            error: function(e) {
                console.log("Error: ", e);
            }
        });

        $.ajax({
            url:"/test/getfamilies",
            dataType: 'json',
            contentType: "application/json",
            cache: false,
            success: function(data) {
                console.log(JSON.stringify(data));
            },
            error: function(e) {
                console.log("Error: ", e);
            }
        });

        $.ajax({
            url:"/test/getfamilymembers",
            dataType: 'json',
            contentType: "application/json",
            cache: false,
            success: function(data) {
                console.log(JSON.stringify(data));
            },
            error: function(e) {
                console.log("Error: ", e);
            }
        });





    }



        function getDataViaAjax(){

            $.ajax({
                url:"/test/getcategories",
                dataType: 'json',
                contentType: "application/json",
                cache: false,
                success: function(data) {

                    console.log("Success: ", data);

                },
                error: function(e) {
                    console.log("Error: ", e);
                },
                done: function(e){
                    console.log("Done");
                }

            })
        }

        function putDataViaAjax(data){

            $.ajax({
                url:"/test/putdatatestajax",
                dataType: 'json',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "html",
                cache: false,
                success: function() {
                    console.log("Success");
                },
                error: function(e) {
                    console.log("Error: ", e);
                },
                done: function(e){
                    console.log("Done");
                }

            })
        }


    function putTask(task){

        $.ajax({
            url:"/test/puttask",
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify(task),
            contentType: "application/json",
            dataType: "html",
            cache: false,
            success: function(data) {
                console.log("Success");
                console.log(data);
            },
            error: function(e) {
                console.log("Error: ", e);
            },
            done: function(e){
                console.log("Done");
            }

        })
    }





    </script>

</head>
<body>
<h1>Testidataa</h1>

<div id="app">

    <form id="getcategorydataform">
        <input type="hidden" id="property1" />
        <button type="submit" id="getdatasubmit">Go get category data</button>
    </form>

    <form id="putcategorydataform">
        <input type="hidden" id="property2" />
        <button type="submit" id="putdatasubmit">Put category data</button>
    </form>


    <form id="getalldataform">
        <input type="hidden" id="property3" />
        <button type="submit" id="getalldatasubmit">Go get all data</button>
    </form>

    <form id="puttaskform">
        <input type="hidden" id="property4" />
        <button type="submit" id="puttasksubmit">Put task</button>
    </form>





</div>

</body>
</html>
