package edu.kpi.comsys.dis.lab.repositories.specifications;

import edu.kpi.comsys.dis.lab.entities.StoriesList;
import edu.kpi.comsys.dis.lab.entities.Story;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class StorySpecificationBuilder {

    private Specification<Story> listSpecification;
    private Map<String, Specification<Story>> fieldSpecifications;

    public static StorySpecificationBuilder story() {
        return new StorySpecificationBuilder();
    }

    protected StorySpecificationBuilder() {
        fieldSpecifications = new LinkedHashMap<>();
    }

    public StorySpecificationBuilder inList(Long listId) {
        addListSpecification(listId);
        return this;
    }

    public StorySpecificationBuilder inList(StoriesList list) {
        return inList(list.getId());
    }

    public StorySpecificationBuilder withType(String type) {
        addFieldSpecifications("type", storyFieldEquals("type", type));
        return this;
    }

    public StorySpecificationBuilder withReporter(String reporter) {
        addFieldSpecifications("reporter", storyFieldEquals("reporter", reporter));
        return this;
    }

    public StorySpecificationBuilder withAssignee(String assignee) {
        addFieldSpecifications("assignee", storyFieldEquals("assignee", assignee));
        return this;
    }

    public StorySpecificationBuilder withTitleContaining(String title) {
        addFieldSpecifications("title", storyFieldContains("title", title));
        return this;
    }

    public StorySpecificationBuilder withDescriptionContaining(String description) {
        addFieldSpecifications("description", storyFieldContains("description", description));
        return this;
    }

    private synchronized void addListSpecification(Long listId) {
        Specification<Story> spec = storyInList(listId);
        listSpecification = listSpecification == null ?
                spec :
                Specifications.where(listSpecification).or(spec);
    }

    private synchronized void addFieldSpecifications(String fieldName, Specification<Story> spec) {
        Specification<Story> fieldSpecification = fieldSpecifications.get(fieldName);
        if (fieldSpecification == null) {
            fieldSpecifications.put(fieldName, spec);
        } else {
            fieldSpecifications.put(fieldName, Specifications.where(fieldSpecification).or(spec));
        }
    }


    public Specification<Story> build() {
        Specification<Story> specification = null;
        if (listSpecification != null) {
            specification = listSpecification;
        }
        for (Specification<Story> fieldSpec: fieldSpecifications.values()) {
            specification = specification != null ?
                    Specifications.where(specification).and(fieldSpec):
                    fieldSpec;
        }
        return specification;
    }

    private static Specification<Story> storyFieldEquals(String fieldName, Object value) {
        return (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }

    private static Specification<Story> storyFieldContains(String fieldName, String substring) {
        final String pattern = "%" + (!StringUtils.isEmpty(substring) ? (substring.toLowerCase() + "%"): "");
        return (root, query, cb) -> cb.like(cb.lower(root.get(fieldName)), pattern);
    }

    private static Specification<Story> storyInList(Long listId) {
        return (root, query, cb) -> cb.equal(root.get("list").get("id"), listId);
    }

}
