# EASY SETUP FOR CREATING TS PROJECT
<sub>Created in November 2026</sub>

## 1. Create project directory and initialize npm
```
mkdir ts-project && cd ts-project
npm init -y
npm install -D typescript @types/node tsx
```

## 2. Generate the Config file
```
npx tsc --init
```

### AI proposed "Modern" TS Config
**`ts.config `**
```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "NodeNext",
    "moduleResolution": "NodeNext",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "outDir": "dist",
    "rootDir": "src"
  },
  "include": ["src"]
}
```
> [!Important]
> The **ts.config** file generated has all of the above settings but the ```rootDir``` and ```outDir``` is commented out. Uncomment them out and create a ```dist``` and ```src``` folder in the root project

## 3. Expand on the scripts
**`package.json`**
```json
"scripts": {
  "dev": "tsx watch src/index.ts",
  "build": "tsc",
  "start": "node dist/index.js"
}
```

## 4. Start project
```
npm run dev
```