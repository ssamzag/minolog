<script setup lang="ts">
import axios from "axios";
import {ref, defineProps, onMounted} from "vue";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

const post = ref({
  id: 0,
  title: "",
  content: ""
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});

const router = useRouter();
const goToEdit = () => {
  router.push({name: "edit", params: {postId: props.postId}})
}
</script>

<template>
  <div>
    <h2>{{post.title}}</h2>
    <div>{{ post.content }}</div>
    <el-button type="warning" @click="goToEdit()">수정</el-button>
  </div>
</template>
