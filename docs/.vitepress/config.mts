import { defineConfig } from "vitepress";

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: "/Monetify/",
  title: "Monetify",
  description: "Static documentation for Monetify using VitePress",
  themeConfig: {
    nav: [
      { text: "Home", link: "/" },
      { text: "Install", link: "/installation" },
      { text: "Supported Apps", link: "/support" },
      { text: "Contributing", link: "/contributing" },
    ],

    sidebar: [
      {
        text: "Docs",
        items: [
          { text: "Home", link: "/" },
          { text: "Installation", link: "/installation" },
          { text: "Supported Apps", link: "/support" },
          { text: "Contributing", link: "/contributing" },
        ],
      },
    ],

    socialLinks: [
      { icon: "github", link: "https://github.com/KaeruShi/Monetify" },
      { icon: "telegram", link: "https://t.me/weeabooify" },
    ],
    footer: {
      message: "Released under the GNU GPL v3.",
      copyright: "Copyright © 2026 Monetify",
    },
    outline: [2, 3],
    lastUpdated: true,
  },
});
