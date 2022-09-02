<template>
  <div>
    <div class="tutorialtitle">
        Recommended Use Order
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">RANDOM</span> page is best used in order of the numbers next to the titles.<br>
        This is <span class="highlighted">not mandatory</span> and you can cherry-pick the generators you wish to use.<br>
        Beware, that if you do not follow the advised order, not all use cases are possible.<br>
        For example if you add Groups to your OO instance after generating Users, the Groups will remain empty!
    </p>
    <div class="tutorialtitle">
        Courses
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Courses</span> section is used to create Courses on your OO instance.<br>
        The <span class="highlighted">AMOUNT</span> input field is where you may specify how many Courses you want to generate.<br>
        The <span class="highlighted">GENERATE</span> button, adds the Courses to OO. This will update the table after finishing.<br>
        The <span class="highlighted">ADD OWNERS</span> button, adds one Owner and one Tutor to all existing Courses.<br>
        The <span class="highlighted">ADD LECTURE</span> button, adds one 4 hour LectureBlock to all existing Courses.<br>
    </p>
    <div class="tutorialtitle">
        Groups
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Groups</span> section is used to create Groups on your OO instance.<br>
        The <span class="highlighted">AMOUNT</span> input field is where you may specify how many Groups you want to generate.<br>
        The <span class="highlighted">GENERATE</span> button, adds the Groups to OO. This will update the table after finishing.<br>
        The <span class="highlighted">ADD OWNERS</span> button, adds one Coach to all existing Groups.<br>
    </p>
    <div class="tutorialtitle">
        Curricula
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Curricula</span> section is used to create Curricula on your OO instance.<br>
        The Curriculum that is generated in this way is predefined, in order to make sense with the random Courses.<br>
        The <span class="highlighted">GENERATE</span> button, adds the Curriculum to OO. This will update the table after finishing.<br>
    </p>
        <div class="tutorialtitle">
        Taxonomies
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Taxonomies</span> section is used to fill up Taxonomies on your OO instance.<br>
        Beware that Taxonomies can not be created from the Datagenerator. You must first manually create one on OO.<br>
        The <span class="highlighted">KEY</span> input field is where enter the Key of the Taxonomy you wish to fill from the table.<br>
        The <span class="highlighted">FILL UP</span> button, fills up the chosen Taxonomy.<br>
    </p>
    <div class="tutorialtitle">
        Users
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Users</span> section is used to create Users on your OO instance.<br>
        The <span class="highlighted">AMOUNT</span> input field is where you may specify how many Users you want to generate.<br>
        The <span class="highlighted">GENERATE</span> button, adds the Users to OO. This will update the table after finishing.<br>
        Users are distributed either put in Groups, Courses or nothing.
    </p>
    <div class="tutorialtitle">
        Roles
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Roles</span> section is used to add Roles to Role-less Users on your OO instance.<br>
        The <span class="highlighted">#USERS</span> input field is where you may specify how many Users should receive the chosen Roles.<br>
        The <span class="highlighted">GIVE ROLES</span> button, adds the Roles to random Role-less Users.<br>
        The <span class="highlighted">box</span> with all Roles is used to pick which Roles you want to assign to Users.<br>
    </p>
  </div>
</template>

<script>
export default {

    name: 'RandomHelper',

}
</script>

<style src="../assets/inputPages.css">

</style>