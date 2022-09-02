<template>
    <div class = "inputPage">
        <LoadingBar v-if="loading"/>
        <div class="titlewrapper">
            <h3 class="sendText">2. Groups</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendBar" type="number" id="groupnmbr" placeholder="AMOUNT"/><br>
                    <input class="sendButton" type="button" value="Generate" @click="create()"/>
                    <form>
                        <input class="sendButton" type="button" value="Add Owners" @click="addOwners()"/>
                    </form>
                </form>
            </div>
            <div class="tableWrapper">
                <h4>{{retrievedGroups.length}} current Groups:</h4>
                <div class="scrollable">  
                    <table class="usertable" >
                        <thead><tr><th></th><th>Key</th><th>Name</th></tr></thead>
                        <tbody id="grouptabledivbody"></tbody>
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

    name: 'GroupCreator',

    components: {
        LoadingBar
    },


    data(){
        return{
            reponse: [],
            retrievedGroups: {},
            data: [],
            loading: false,

        }
    },

    async mounted(){
        await api.getGroups().then(response =>{
            this.retrievedGroups = response.data;
        })
        this.showGroups();
    },

    methods:{

        create: async function(){
            if(document.getElementById("groupnmbr").value < 1){
                alert("Can't create less than 1 group");
            } else {
                this.loading = true;
                await api.createGroups(document.getElementById("groupnmbr").value) 
                await api.getGroups().then(response =>{
                    this.retrievedGroups = response.data;
                }) 
                this.loading = false;
                this.showGroups();

            }     
        },

        showGroups: async function(){
        
            this.data.length = 0;
            for(var i=0; i<this.retrievedGroups.length; i++){
                this.data.push(this.retrievedGroups[i].key);
                this.data.push(this.retrievedGroups[i].name);
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
                    mytable += "<td>" + cell + "</td></tr>"; 
                    count = 1;
                }
            }
            document.getElementById("grouptabledivbody").innerHTML = mytable;
        },

        addOwners: async function(){
            await api.addOwnersToGroups()
        }
    }

}
</script>

<style src="../assets/inputPages.css">

</style>