#!/bin/bash
set -e

# ========= REPO ROOT =========
REPO_ROOT=$(git rev-parse --show-toplevel)

# ========= CONFIG =========

BASE_PACKAGE="com.handmade.ecommerce.stm"
BASE_XML_PATH="stm/stm"
ENTITY_PACKAGE="com.handmade.ecommerce.domain"

XML_DIR="$REPO_ROOT/handmade-stm-management/src/main/resources/stm/stm"
OUTPUT_BASE="$REPO_ROOT/handmade-stm-configuration/src/main/java"
OUTPUT_DIR="$OUTPUT_BASE/$(echo $BASE_PACKAGE | tr '.' '/')"

mkdir -p "$OUTPUT_DIR"

# ===== kebab-case -> PascalCase =====
to_pascal() {
  echo "$1" | awk -F- '{
    for (i=1; i<=NF; i++)
      printf toupper(substr($i,1,1)) substr($i,2);
  }'
}

# ===== kebab-case -> camelCase =====
to_camel() {
  pascal=$(to_pascal "$1")
  echo "$(tr '[:upper:]' '[:lower:]' <<< ${pascal:0:1})${pascal:1}"
}

for xml in "$XML_DIR"/*-states.xml; do
  file=$(basename "$xml")
  base=${file%-states.xml}

  camel=$(to_camel "$base")
  pascal=$(to_pascal "$base")

  class_name="${pascal}StmConfiguration"
  entity="${pascal}"

  java_file="$OUTPUT_DIR/$class_name.java"

  echo "🚀 Generating $class_name"

  cat > "$java_file" <<EOF
package $BASE_PACKAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.chenile.stm.STM;
import org.chenile.stm.STMActionsInfoProvider;
import org.chenile.stm.impl.STMFlowStoreImpl;
import org.chenile.stm.impl.STMImpl;
import org.chenile.stm.xml.XmlFlowReader;
import org.chenile.core.context.BeanFactoryAdapter;
import org.chenile.core.context.SpringBeanFactoryAdapter;

import $ENTITY_PACKAGE.$entity;

@Configuration
public class $class_name {

    private static final String FLOW_DEFINITION_FILE =
            "$BASE_XML_PATH/$file";

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    BeanFactoryAdapter ${camel}BeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // -------- Flow Store --------
    @Bean
    STMFlowStoreImpl ${camel}FlowStore(
            @Qualifier("${camel}BeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    // -------- STM --------
    @Bean
    @Autowired
    STM<$entity> ${camel}EntityStm(
            @Qualifier("${camel}FlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMImpl<$entity> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    // -------- Actions Info --------
    @Bean
    @Autowired
    STMActionsInfoProvider ${camel}ActionsInfoProvider(
            @Qualifier("${camel}FlowStore")
            STMFlowStoreImpl stmFlowStore) {

        return new STMActionsInfoProvider(stmFlowStore);
    }

    // -------- XML Flow Reader --------
    @Bean
    XmlFlowReader ${camel}FlowReader(
            @Qualifier("${camel}FlowStore")
            STMFlowStoreImpl flowStore) throws Exception {

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
@Entity
class $entity extends AbstractExtendedStateEntity {
}
EOF

done

echo "✅ All Chenile STM configuration classes generated successfully."