const { generateStories } = require("@storybook/native");
const { pascalCase } = require("change-case");
const stories = require('./stories.json')
const fs = require('fs');

fs.rmSync('./stories', { recursive: true, force: true });

const promises = stories.map(async (component) => {
    return generateStories({
        category: pascalCase(component.name),
        filePath: `./stories/${component.name}.stories.jsx`,
        platform: "android",
        stories: [
            {
                name: "Example",
                appParams: {
                    component: component.name
                },
//                docs: docsRequest.data
            }
        ],
        controls: component.control
            ? Object.entries(component.control)
            : undefined,
        deepLinkUrl: "sb-native://deep.link"
    });
});
Promise.all(promises).catch((err) => {
    console.error(err);
    process.exit(1);
});