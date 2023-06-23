package com.fadavidos.demoSpringWeb;

import com.fadavidos.demoSpringWeb.controllers.VideoEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class CoreDomainTest {

    @Test
    void newVideoEntityShouldHaveNullId(){
        VideoEntity entity = new VideoEntity("title", "description", "alice");
        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isEqualTo("alice");
        assertThat(entity.getName()).isEqualTo("title");
        assertThat(entity.getDescription()).isEqualTo("description");
    }

    @Test
    void toStringShouldBeAlsoTested(){
        VideoEntity entity = new VideoEntity("title", "description", "alice");
        assertThat(entity.toString()).isEqualTo("VideoEntity{id=null, username='alice', " +
                "name='title', description='description'}");
    }

    @Test
    void settersShouldMutateState(){
        VideoEntity entity = new VideoEntity("title", "description", "alice");
        entity.setDescription("new description");
        entity.setName("new title");
        entity.setUsername("new alice");
        entity.setId(1234L);
        assertThat(entity.getDescription()).isEqualTo("new description");
        assertThat(entity.getName()).isEqualTo("new title");
        assertThat(entity.getUsername()).isEqualTo("new alice");
        assertThat(entity.getId()).isEqualTo(1234L);
    }
}
