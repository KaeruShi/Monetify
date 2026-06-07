import { defineConfig } from "vitepress";

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: "/Monetify/",
  title: "Monetify",
  description: "Static documentation for Monetify using VitePress",

  head: [
    [
      "link",
      {
        rel: "icon",
        type: "image/svg+xml",
        href: "/Monetify/monetify-icon.svg",
      },
    ],
  ],


  themeConfig: {
    logo: { src: "/monetify-icon.svg", alt: "Monetify Logo" },
    nav: [
      { text: "Home", link: "/" },
      { text: "Guide", link: "/guide" },
      { text: "Supported Apps", link: "/support" },
      { text: "Contributing", link: "/contributing" },
    ],

    sidebar: [
      {
        text: "Docs",
        items: [
          { text: "Home", link: "/" },
          { text: "Guide", link: "/guide" },
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
      message: "Released under the GNU GPL v3 License",
      copyright: "Copyright © 2026 KaeruShi",
    },
    outline: [2, 3],
  },
});
