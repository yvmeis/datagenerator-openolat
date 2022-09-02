<template>
    <div class = "inputPage">
        <LoadingBar v-if="loading"/>
        <div class="titlewrapper">
            <h3 class="sendText">5. Users</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendBar" type="number" id="usernmbr" placeholder="AMOUNT"/><br>
                    <input class="sendButton" type="button" value="Generate" @click="create()"/>
                </form>
            </div>
            <div class="tableWrapper">
                <h4>{{retrievedUsers.length}} current Users:</h4>
                <div class="scrollable">  
                    <table class="usertable" >
                        <thead><tr><th></th><th>Key</th><th>First Name</th><th>Last Name</th></tr></thead>
                        <tbody id="usertabledivbody"></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import api from "../api/backend-api"
import LoadingBar from "../components/LoadingBar";

function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

export default {

    name: 'userCreator',

    components: {
        LoadingBar
    },

    data(){
        return{
            reponse: [],
            retrievedUsers: {},
            data: [],
            loading: false,

        }
    },

    async mounted(){
        await api.getUsers().then(response =>{
            this.retrievedUsers = response.data;
        })
        this.showUsers();
    },

    methods: {
        create: async function(){
            if(document.getElementById("usernmbr").value < 1){
                alert("Can't create less than 1 user");
            } else {
                this.loading=true;
                await api.createUsers(document.getElementById("usernmbr").value)  
                await api.getUsers().then(response =>{
                    this.retrievedUsers = response.data;
                })
                this.loading=false;
                this.showUsers();
            }  
        },

        showUsers: async function(){
            
            this.data.length = 0;
            for(var i=0; i<this.retrievedUsers.length; i++){
                this.data.push(this.retrievedUsers[i].key);
                this.data.push(this.retrievedUsers[i].firstName);
                this.data.push(this.retrievedUsers[i].lastName);
            }
            await delay(1);
            this.createTable();
        },

        createTable: async function(){
            var mytable="";
            var count = 1;
            for (var cell of this.data) { 
                if (count === 1){
                    mytable += "<tr><td class='tdstarter'></td>";
                    mytable += "<td>" + cell + "</td>"; 
                    count = 2;
                } else if (count === 2){
                    mytable += "<td>" + cell + "</td>"; 
                    count = 3;
                } else {
                    mytable += "<td>" + cell + "</td></tr>"; 
                    count = 1;
                }
                }
            document.getElementById("usertabledivbody").innerHTML = mytable;
        },

    },
}

</script>

<style src="../assets/inputPages.css">

</style>