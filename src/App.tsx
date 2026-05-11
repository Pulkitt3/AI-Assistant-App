/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React from 'react';
import { Smartphone, Download, Github, Code2, Database, Cpu } from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-200 font-sans p-8 flex flex-col items-center justify-center">
      <div className="max-w-3xl w-full space-y-8 bg-slate-900/50 p-12 rounded-3xl border border-slate-800 backdrop-blur-xl shadow-2xl">
        <div className="flex flex-col items-center text-center space-y-4">
          <div className="p-4 bg-blue-500/10 rounded-2xl border border-blue-500/20">
            <Smartphone className="w-16 h-16 text-blue-400" />
          </div>
          <h1 className="text-4xl font-bold tracking-tight text-white">Native Android AI Chatbot</h1>
          <p className="text-xl text-slate-400">
            This project has been fully configured as a <span className="text-blue-400 font-semibold">Native Android App</span> using Kotlin, Jetpack Compose, and Clean Architecture.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <FeatureCard 
            icon={<Cpu className="w-5 h-5" />} 
            title="Gemini AI Integration" 
            desc="Real-time LLM responses with context-aware RAG querying." 
          />
          <FeatureCard 
            icon={<Database className="w-5 h-5" />} 
            title="Room Database" 
            desc="Persistent chat history and PDF context storage local to the device." 
          />
          <FeatureCard 
            icon={<Code2 className="w-5 h-5" />} 
            title="Clean Architecture" 
            desc="Robust Domain layer with Use Cases, separating business logic from UI." 
          />
          <FeatureCard 
            icon={<Github className="w-5 h-5" />} 
            title="Unit Testing" 
            desc="Tests for Use Cases and ViewModels using JUnit 4 and Mockito." 
          />
        </div>

        <div className="pt-8 border-t border-slate-800">
          <div className="bg-slate-950/50 p-6 rounded-2xl border border-slate-800/50 space-y-4">
            <h2 className="text-lg font-semibold text-white flex items-center gap-2">
              <Github className="w-5 h-5" />
              How to use this project
            </h2>
            <ol className="list-decimal list-inside space-y-2 text-slate-400">
              <li>Open the <span className="text-slate-200">File Explorer</span> to see the full Android project structure.</li>
              <li>Use the <span className="text-slate-200">Export</span> menu (Top Right) to download as a ZIP or publish to GitHub.</li>
              <li>Import the project into <span className="text-slate-200">Android Studio</span>.</li>
              <li>Add your <span className="text-blue-400">Gemini API Key</span> in <code className="bg-slate-800 px-1 rounded text-xs text-blue-300">MainActivity.kt</code>.</li>
            </ol>
          </div>
        </div>
      </div>
      
      <p className="mt-8 text-slate-500 text-sm">
        Note: Native Android code cannot be executed directly in this web preview.
      </p>
    </div>
  );
}

function FeatureCard({ icon, title, desc }: { icon: React.ReactNode, title: string, desc: string }) {
  return (
    <div className="p-5 bg-slate-800/30 rounded-2xl border border-slate-800/50 hover:border-slate-700 transition-colors">
      <div className="flex items-center gap-3 mb-2 text-blue-400">
        {icon}
        <h3 className="font-semibold text-slate-100">{title}</h3>
      </div>
      <p className="text-sm text-slate-400 leading-relaxed">{desc}</p>
    </div>
  );
}

