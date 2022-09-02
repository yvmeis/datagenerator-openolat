<template>
    <div class = "inputPage">
        <div class="titlewrapper">
            <h3 class="sendText">6. Roles</h3>
            <div class="titleborder"></div>
        </div>
        <div class="contentwrapper">
            <div>
                <form class="classicbuttonform">
                    <input class="sendBar" type="number" id="rolenmbr" placeholder="# USERS"/><br>
                    <input class="sendButton" type="button" value="Give Roles" @click="create()"/>
                </form>
            </div>
            <form class="checkboxform">
                <label for="bx1"> systemAdmin: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx1"/><br>
                <label for="bx2"> olatAdmin: </label>        
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx2"/><br>
                <label for="bx3"> userManager: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx3"/><br>
                <label for="bx4"> groupManager: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx4"/><br>
                <label for="bx5"> author: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx5"/><br>
                <label for="bx7"> ResourceManager: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx7"/><br>
                <label for="bx8"> poolAdmin: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx8"/><br>
                <label for="bx9"> curriculumManager: </label>
                <input @click="updateRoles()" class="sendCheckBox" type="checkbox" id="bx9"/>
            </form>
        </div>
        <h4>{{this.retrievedKeys.length}} Users already possess these Roles</h4>
    </div>
</template>

<script>

import api from "../api/backend-api"

export default {

name: 'RoleDistribution',

    data(){
        return{
            usersInSystem:0,
            retrievedUsers:[],
            userRoles: [],
            retrievedKeys: [],

        }
    },

    mounted(){
        this.updateRoles()
    },

    methods:{

        create: async function(){

            await api.getUsers().then(response =>{
                this.retrievedUsers = response.data;
            })

            this.usersInSystem = this.retrievedUsers.length;

            if (document.getElementById("rolenmbr").value > this.usersInSystem){

                alert("There are not sufficiently many users in the system");
            } else {

                await api.setRoles(document.getElementById("rolenmbr").value,this.userRoles)
                this.updateRoles();
            }
        },

        updateRoles: async function(){

            this.userRoles.length = 0;

            if (document.getElementById("bx1").checked){
                await this.userRoles.push("systemAdmin");
            }
            if (document.getElementById("bx2").checked){
                await this.userRoles.push("olatAdmin");
            }
            if (document.getElementById("bx3").checked){
                await this.userRoles.push("userManager");
            }
            if (document.getElementById("bx4").checked){
                await this.userRoles.push("groupManager");
            }
            if (document.getElementById("bx5").checked){
                await this.userRoles.push("author");
            }
            if (document.getElementById("bx7").checked){
                await this.userRoles.push("institutionalResourceManager");
            }
            if (document.getElementById("bx8").checked){
                await this.userRoles.push("poolAdmin");
            }
            if (document.getElementById("bx9").checked){
                await this.userRoles.push("curriculumManager");
            }
            if (this.userRoles.length === 0){
                await this.userRoles.push("NOROLES")
            }

            await api.getUsersWithRoles(this.userRoles).then(response =>{
                this.retrievedKeys = response.data;
            })
        }
    }
}
</script>

<style src="../assets/inputPages.css">

</style>