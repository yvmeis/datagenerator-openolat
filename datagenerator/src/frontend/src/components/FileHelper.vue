<template>
  <div>
    <div class="tutorialtitle">
      File Structure
    </div>
    <div style="display:flex; flex-direction: row; margin-right: 5vw; align-items: center;">
      <p class="tutorialtext">
        All files must be saved in the <span class="highlighted">.csv</span> format!<br>
        The <span class="highlighted">File Structure</span> is of the utmost importance. It must not be tampered with.<br>
        A <span class="highlighted">Custom Template</span>, named "custom1" in the example, must contain all of the shown folders.<br>
        The only thing that can be changed is the number of <span class="highlighted">Taxonomies and Curricula</span>.<br>
        You may leave those folders empty or have an arbitrary number. Always name them with a number as in the example.
      </p>
      <img style="height: 22vh; width: 19vh;" src = "../assets/example-file-structure.jpg"/>
    </div>
      <div class="tutorialtitle">
        Courses
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">courses</span> folder must contain exactly one file: <span class="highlighted">courses.csv</span><br>
          In this file all <span class="highlighted">Courses</span> must be defined.<br>
          In the <span class="highlighted">1st column</span> is the name of the Course.<br>
          In the <span class="highlighted">2nd column</span> is the name of the .zip file you wish to fill the Course with. This can be left empty.<br>
          In the <span class="highlighted">3rd column</span> is wether the Course has Lectures enabled.<br>
          In the <span class="highlighted">4th column</span> is the number of random generated owners.<br>
          In the <span class="highlighted">5th column</span> is the number of random generated tutors.<br>
        </p>
        <label>courses.csv</label>
        <img style="height: 16vh; width: 50vh;" src = "../assets/courses.jpg"/>
      </div>
      <div class="tutorialtitle">
        CourseZips
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">courseZips</span> folder must contain all .zip exported Courses that were mentioned in the courses.csv<br>
          Make sure the names are exactly the same!
        </p>
      </div>
      <div class="tutorialtitle">
        Curricula
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">courseZips</span> folder must contain all .zip exported Courses that were mentioned in the courses.csv<br>
          Make sure the names are exactly the same!
        </p>
      </div>
      <div class="tutorialtitle">
        Groups
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">groups</span> folder must contain exactly one file: <span class="highlighted">groups.csv</span><br>
          In this file all <span class="highlighted">Groups</span> must be defined.<br>
          In the <span class="highlighted">1st column</span> is the name of the Group.<br>
          In the <span class="highlighted">2nd column</span> number of random generated coaches.<br>
          In the <span class="highlighted">3rd - nth column</span> are the Course Names to which the groups shall belong.<br>
        </p>
        <label>groups.csv</label>
        <img style="height: 16vh; width: 60vh;" src = "../assets/groups.jpg"/>
      </div>
      <div class="tutorialtitle">
        Taxonomies
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">courseZips</span> folder must contain all .zip exported Courses that were mentioned in the courses.csv<br>
          Make sure the names are exactly the same!
        </p>
      </div>
      <div class="tutorialtitle">
        Users
      </div>
      <div style="display:flex; flex-direction: column; align-items: center;">
        <p class="tutorialtext">
          The <span class="highlighted">users</span> folder must contain exactly one file: <span class="highlighted">users.csv</span><br>
          In this file all non-random <span class="highlighted">Users</span> must be defined.<br>
          In the <span class="highlighted">1st column</span> is the username of the User.<br>
          In the <span class="highlighted">2nd column</span> is the password of the User.<br>
          In the <span class="highlighted">3rd column</span> is the firstname of the User.<br>
          In the <span class="highlighted">4th column</span> is the gender of the User. This is solely for the purposes of picking a profilepicture<br>
          From the <span class="highlighted">5th column onward</span> you can define where the User is put.<br>
          Always start off with a identifier in <span class="highlighted">all Caps</span>. They are COURSE,CURRICULUM,GROUP and ROLES.<br>
          After <span class="highlighted">COURSE</span> you must add the Course name and then the desired roles: participant, tutor or owner.<br>
          After <span class="highlighted">GROUP</span> you must add the Group name and then the desired roles: participant or owner.<br>
          After <span class="highlighted">CURRICULUM</span> you must add the Curriculum name and then <span class="highlighted">ELEMENT</span><br>
          After <span class="highlighted">ELEMENT</span> you must add the name of the desired Curriculum Elements in the aforementioned Curriculum.<br>
          After <span class="highlighted">ROLES</span> you must add the names of the Roles you wish this User to have. The possible Roles are as follows:<br>
        </p>
        <p class="tutorialtext">
          "systemAdmin"<br>
          "olatAdmin"<br>
          "userManager"<br>
          "groupManager"<br>
          "author"<br>
          "institutionalResourceManager"<br>
          "poolAdmin"<br>
          "curriculumManager"<br>
        </p>
        <label>users.csv</label>
        <img style="height: 16vh; width: 90vh;" src = "../assets/users.jpg"/>
      </div>
  </div>
</template>

<script>
export default {

    name: 'FileHelper',

}
</script>

<style src="../assets/inputPages.css">

</style>