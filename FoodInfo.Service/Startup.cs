﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.EntityFrameworkCore;
using FoodInfo.Service.Models;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Server.HttpSys;
using AutoMapper;
using FoodInfo.Service.DTOs;

namespace FoodInfo.Service
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
            Mapper.Initialize((cfg => {
                 cfg.CreateMap<User, UserDTO>();
                 cfg.CreateMap<UserDTO, User>();
                cfg.CreateMap<ModeratorDTO, User>();
                cfg.CreateMap<AdminDTO, User>();
                cfg.CreateMap<ErrorDTO, Error>();
                cfg.CreateMap<Error, ErrorDTO>();
                cfg.CreateMap<LanguageDTO, Language>();
                cfg.CreateMap<CategoryNameDTO, ProductCategory>();
                cfg.CreateMap<ProductCategory, CategoryNameDTO>();
                cfg.CreateMap<ProductContent, ContentDTO>();
                cfg.CreateMap<ContentDTO, ProductContent>();
                cfg.CreateMap<ContentDTO, Comment>();
                cfg.CreateMap<CommentDTO, Comment>();
                cfg.CreateMap<ContentDTO, Vote>();
                cfg.CreateMap<Product, ProductDTO>();
                cfg.CreateMap<ProductCategory, ProductCategoryDTO>();
                cfg.CreateMap<ProductCategoryDTO, ProductCategory>();
                cfg.CreateMap<SearchByNameDTO, Product>();
                cfg.CreateMap<Product, SearchByNameDTO>();  
;                


            } ));
           

        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddAuthentication(HttpSysDefaults.AuthenticationScheme);


           // var connection = @"Server=18.191.129.27; Database=FoodInforService; User Id=fiservice;assword=foodinfoservice;ConnectRetryCount=0";

            services.AddDbContext<FoodInfoServiceContext>(options =>
                    options.UseSqlServer(Configuration.GetConnectionString("FoodInfoServiceContext")));


        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseAuthentication();
            app.UseHttpsRedirection();
            app.UseMvc();
        }
    }
}
