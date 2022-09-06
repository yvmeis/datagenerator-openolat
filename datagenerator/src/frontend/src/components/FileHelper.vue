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
          The <span class="highlighted">curricula</span> folder can contain arbitrarily many curricula. They must all be named: "curriculum{number}"<br>
          Each curriculum folder must contain a <span class="highlighted">coursecurriculumrelations.csv</span>, <span class="highlighted">curriculumname.csv</span>, <span class="highlighted">levels.csv</span> and <span class="highlighted">types.csv</span>.
        </p>
        <img style="height: 16vh; width: 24vh;" src = "../assets/curriculum-structure.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the coursecurriculumrelations.csv file you must put the name of Curriculum Element.<br>
          In the <span class="highlighted">2nd - nth column</span> you must put the name of the Course you wish to add to the Element.
        </p>
        <label>coursecurriculumrelations.csv</label>
        <img style="height: 28vh; width: 69vh;" src = "../assets/curriculumrelations.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the curriculumname.csv file you must put the name of Curriculum.<br>
          In the <span class="highlighted">2nd column</span> you must put the identifier of the Curriculum.
        </p>
        <label>curriculumname.csv</label>
        <img style="height: 9vh; width: 30vh;" src = "../assets/curriculumnames.jpg"/>
        <p class="tutorialtext">
          -----------------------------------------------------------
        </p>
        <label>levels.csv</label>
        <img style="height: 8vh; width: 45vh;" src = "../assets/groups.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the types.csv file you must put the name of type.<br>
          In the <span class="highlighted">2nd column</span> you must put the identifier of the type.
        </p>
        <label>types.csv</label>
        <img style="height: 16vh; width: 40vh;" src = "../assets/curriculumtypes.jpg"/>
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
          The <span class="highlighted">taxonomies</span> folder can contain arbitrarily many taxonomies. They must all be named: "taxonomy{number}"<br>
          Each taxonomy folder must contain a <span class="highlighted">coursetaxonomyrelations.csv</span>, <span class="highlighted">taxonomyname.csv</span>, <span class="highlighted">levels.csv</span> and <span class="highlighted">types.csv</span>.
        </p>
        <img style="height: 16vh; width: 24vh;" src = "../assets/taxonomy-structure.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the coursetaxonomyrelations.csv file you must put the name of Taxonomy Level.<br>
          In the <span class="highlighted">2nd - nth column</span> you must put the name of the Course you wish to add to the Level.
        </p>
        <label>coursetaxonomyrelations.csv</label>
        <img style="height: 26vh; width: 77vh;" src = "../assets/taxonomyrelations.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the taxonomyname.csv file you must put the name of Taxonomy.<br>
          Remember to create the Taxonomy in the OO instance!
        </p>
        <label>taxonomyname.csv</label>
        <img style="height: 9vh; width: 20vh;" src = "../assets/taxonomyname.jpg"/>
        <p class="tutorialtext">
          -----------------------------------------------------------
        </p>
        <label>levels.csv</label>
        <img style="height: 16vh; width: 60vh;" src = "../assets/groups.jpg"/>
        <p class="tutorialtext">
          In the <span class="highlighted">1st column</span> of the types.csv file you must put the name of type.<br>
          In the <span class="highlighted">2nd column</span> you must put the identifier of the type.
        </p>
        <label>types.csv</label>
        <img style="height: 16vh; width: 32vh;" src = "../assets/taxonomytypes.jpg"/>
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
          Always start off with an identifier in <span class="highlighted">all Caps</span>. They are COURSE, CURRICULUM, GROUP and ROLES.<br>
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
        <img style="height: 15vh; width: 90vh;" src = "../assets/users.jpg"/>
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